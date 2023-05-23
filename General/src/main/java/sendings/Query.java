package sendings;

import arguments.ReadableArguments;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Query implements Serializable {
    public Query(String commandName, ReadableArguments<?> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }
    private String commandName;
    private ReadableArguments<?> arguments;

    public ReadableArguments getParser() {
        return arguments;
    }
    public String getCommandName() {
        return commandName;
    }

    public byte[] getBytes() throws  IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        return baos.toByteArray();
    }
}
