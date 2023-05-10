package logic;

import elements.Location;
import elements.Person;

import java.util.ArrayList;
import java.util.Hashtable;

public interface Manager {
    boolean containsKey(String s);

    void put(String parameter, Person element);

    void update(String key, Person element);

    void remove(String parameter);

    ArrayList<String> removeGreater(String parameter);

    ArrayList<String> removeLower(Person element);

    void clear();

    int countByWeight(double weight);

    ArrayList<Person> filterByLocation(Location readElement);

    int countGreaterThanLocation(Location readElement);

    Hashtable<String, Person> getCollection();

    ArrayList<String> findById(int id);

    String getInfo();


}
