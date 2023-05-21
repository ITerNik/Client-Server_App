package sendings;

import commands.ArgumentParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Query implements Serializable {
    public Query(String commandName, ArgumentParser parser) {
        this.commandName = commandName;
        this.parser = parser;
    }
    private String commandName;
    private ArgumentParser parser;

    public ArgumentParser getParser() {
        return parser;
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
