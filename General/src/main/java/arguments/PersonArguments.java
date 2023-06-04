package arguments;

import elements.Person;
import logic.IODevice;

public class PersonArguments implements Readable<Person> {
    public PersonArguments() {
    }
    @Override
    public Person read(IODevice io) {
         return io.readElement(Person.class);
    }
}
