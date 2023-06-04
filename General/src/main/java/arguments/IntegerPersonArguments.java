package arguments;

import constants.Messages;
import elements.Person;
import exceptions.BadParametersException;
import logic.IODevice;

import java.util.AbstractMap.SimpleEntry;

public class IntegerPersonArguments implements Readable<SimpleEntry<Integer, Person>> {
    @Override
    public SimpleEntry<Integer, Person> read(IODevice from) {
        int input;
        try {
            input =Integer.parseInt(from.read());
        } catch (NumberFormatException e) {
            throw new BadParametersException(Messages.getMessage("warning.format.not_integer",
                    Messages.getMessage("parameter.id")));
        }
        return new SimpleEntry<>(input, from.readElement(Person.class));
    }
}
