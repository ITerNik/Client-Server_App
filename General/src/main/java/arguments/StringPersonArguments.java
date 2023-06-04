package arguments;

import elements.Person;
import logic.IODevice;

import java.util.AbstractMap.SimpleEntry;
public class StringPersonArguments implements Readable<SimpleEntry<String, Person>> {
    public StringPersonArguments(){
    }
    @Override
    public SimpleEntry<String, Person> read(IODevice io) {
        return new SimpleEntry<>(io.read(), io.readElement(Person.class));
    }
}
