package arguments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import constants.Messages;
import elements.Person;
import exceptions.BadParametersException;
import logic.IODevice;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public class IntegerPersonArguments implements Readable {
    @Override
    public String read(IODevice from) throws JsonProcessingException {
        int input;
        try {
            input = Integer.parseInt(from.read());
        } catch (NumberFormatException e) {
            throw new BadParametersException(Messages.getMessage("warning.format.not_integer",
                    Messages.getMessage("parameter.id")));
        }
        from.readLine();
        Entry<Integer, Person> entry = new SimpleEntry<>(input, from.readElement(Person.class));
        return mapper.writerFor(new TypeReference<Entry<Integer, Person>>() {}).writeValueAsString(entry);
    }
}
