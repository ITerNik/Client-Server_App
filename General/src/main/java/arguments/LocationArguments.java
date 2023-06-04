package arguments;

import elements.Location;
import logic.IODevice;
public class LocationArguments implements Readable<Location> {
    public LocationArguments(){
    }
    @Override
    public Location read(IODevice io) {
         return io.readElement(Location.class);
    }
}
