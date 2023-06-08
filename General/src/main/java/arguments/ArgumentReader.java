package arguments;

import com.fasterxml.jackson.core.JsonProcessingException;
import logic.IODevice;

public class ArgumentReader {
    private String argument;

    private Readable reader;

    public ArgumentReader() {
    }

    public ArgumentReader(Readable reader) {
        this.reader = reader;
    }

    public void read(IODevice io) {
        try {
            argument = reader.read(io);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public String getArgument() {
        return argument;
    }

    public Readable getReader() {
        return reader;
    }
}
