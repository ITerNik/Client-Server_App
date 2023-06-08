package arguments;

import com.fasterxml.jackson.core.JsonProcessingException;
import elements.Person;
import logic.IODevice;

public class PersonArguments implements Readable {
    public PersonArguments() {
    }
    @Override
    public String read(IODevice io) throws JsonProcessingException {
         return mapper.writerFor(Person.class).writeValueAsString(io.readElement(Person.class));
    }
}
