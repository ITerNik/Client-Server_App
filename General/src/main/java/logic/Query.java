package logic;

import commands.ArgumentParser;

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

}
