package arguments;

import logic.IODevice;

public class ArgumentReader<T> {
    private T argument;

    private Readable<T> reader;

    public ArgumentReader() {
    }

    public ArgumentReader(Readable<T> reader) {
        this.reader = reader;
    }

    public void read(IODevice io) {
        argument = reader.read(io);
    }

    public T getArgument() {
        return argument;
    }

    public Readable<T> getReader() {
        return reader;
    }
}
