package arguments;

import logic.IODevice;
public class KeyArguments implements Readable<String> {
    public KeyArguments(){
    }
    @Override
    public String read(IODevice io) {
        return io.read();
    }
}
