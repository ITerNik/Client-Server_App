package logic;

import elements.Location;
import elements.Person;
import exceptions.BadParametersException;
import exceptions.NonUniqueIdException;
import constants.Messages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class CollectionManager implements Manager {
    private final Hashtable<String, Person> collection;
    private final DependentSet<Integer> uniqueSet = new DependentSet<>();
    private final LocalDateTime date;

    AtomicInteger idCounter = new AtomicInteger(0);

    {
        date = LocalDateTime.now();
    }

    public CollectionManager(Hashtable<String, Person> collection) throws NonUniqueIdException {
        this.collection = collection;
        for (String key : collection.keySet()) {
            if (!uniqueSet.add(collection.get(key).getId()))
                throw new NonUniqueIdException(Messages.getMessage("warning.non_unique_id"));
        }
    }

    public class DependentSet<T extends Comparable<T>> {
        private final TreeSet<T> unique = new TreeSet<>();

        public boolean add(T value) {
            return unique.add(value);
        }

        public boolean remove(T value) {
            return unique.remove(value);
        }

        public boolean replace(T oldValue, T newValue) {
            return unique.remove(oldValue) && unique.add(newValue);
        }

        public void clear() {
            unique.clear();
        }
    }

    private ArrayList<String> getKeyIf(Predicate<? super String> filter) {
        ArrayList<String> selected = new ArrayList<>();
        for (String currKey : collection.keySet()) {
            if (filter.test(currKey)) {
                selected.add(currKey);
            }
        }
        return selected;
    }

    public void put(String key, Person person) {
        while (!uniqueSet.add(idCounter.get())) {
            idCounter.incrementAndGet();
        }
        person.setId(idCounter.get());
        person.setCreationDate(LocalDate.now());
        collection.put(key, person);
    }


    public void remove(String key) {
        uniqueSet.remove(collection.get(key).getId());
        collection.remove(key);
    }


    public void update(String key, Person person) {
        uniqueSet.replace(collection.get(key).getId(), person.getId());
        collection.replace(key, person);
    }

    public boolean containsKey(String arg) {
        return collection.containsKey(arg);
    }

    public void clear() {
        collection.clear();
        uniqueSet.clear();
    }

    public int countByWeight(double weight) {
        return getKeyIf(currKey -> Double.compare(collection.get(currKey).getWeight(), weight) == 0).size();
    }

    public ArrayList<String> findById(int id) {
        ArrayList<String> selected = getKeyIf(currKey -> collection.get(currKey).getId() == id);
        if (!selected.isEmpty()) return selected;
        else
            throw new BadParametersException(Messages.getMessage("warning.format.no_such_element", Messages.getMessage("parameter.id")));
    }

    public int countGreaterThanLocation(Location location) {
        return getKeyIf(currKey -> {
            Location curr = collection.get(currKey).getLocation();
            return curr != null && curr.compareTo(location) > 0;
        }).size();
    }

    public ArrayList<Person> filterByLocation(Location location) {
        ArrayList<String> selected = getKeyIf(currKey -> {
            Location curr = collection.get(currKey).getLocation();
            return curr != null && curr.equals(location);
        });
        ArrayList<Person> res = new ArrayList<>();
        if (selected.isEmpty())
            throw new IllegalArgumentException(Messages.getMessage("warning.format.no_such_element", Messages.getMessage("parameter.location")));
        else {
            for (String key : selected) {
                res.add(collection.get(key));
            }
            return res;
        }
    }


    public ArrayList<String> removeGreater(String key) {
        ArrayList<String> removed = getKeyIf(currKey -> currKey.compareTo(key) > 0);
        for (String currKey : removed) {
            uniqueSet.remove(collection.get(currKey).getId());
            collection.remove(currKey);
        }
        return removed;
    }

    public ArrayList<String> removeLower(Person element) {
        ArrayList<String> removed = getKeyIf(currKey -> collection.get(currKey).compareTo(element) < 0);
        for (String currKey : removed) {
            uniqueSet.remove(collection.get(currKey).getId());
            collection.remove(currKey);
        }
        return removed;
    }

    public Hashtable<String, Person> getCollection() {
        return collection;
    }

    public String getInfo() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss");
        return String.format(Messages.getMessage("collection.type") + ": %s\n" +
                        Messages.getMessage("collection.elements") + ": %s\n" +
                        Messages.getMessage("collection.date") + ": %s\n" +
                        Messages.getMessage("collection.size") + ": %s",
                collection.getClass().getSimpleName(), Messages.getMessage("parameter.person"), date.format(format), collection.size());
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) {
            return Messages.getMessage("message.empty");
        }
        StringBuilder res = new StringBuilder();
        for (String key : collection.keySet()) {
            res.append(String.format("%s \"%s\":\n%s\n\n", Messages.getMessage("parameter.person"), key, collection.get(key)));
        }
        return res.toString();
    }
}
