package arguments;

import logic.IODevice;
public class KeyArguments implements Readable {
    public KeyArguments(){
    }
    @Override
    public String read(IODevice io) {
        return io.read();
    }
}
