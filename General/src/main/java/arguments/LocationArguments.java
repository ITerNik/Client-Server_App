package arguments;

import com.fasterxml.jackson.core.JsonProcessingException;
import elements.Location;
import logic.IODevice;
public class LocationArguments implements Readable {
    public LocationArguments(){
    }
    @Override
    public String read(IODevice io) throws JsonProcessingException {
         return mapper.writerFor(Location.class).writeValueAsString(io.readElement(Location.class));
    }
}
