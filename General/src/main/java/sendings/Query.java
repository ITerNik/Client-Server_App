package sendings;

import arguments.ArgumentReader;

import java.io.Serializable;
public class Query implements Serializable {
    public Query(String commandName, ArgumentReader<?> arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }
    public Query() {
    }

    private String commandName;
    private ArgumentReader<?> arguments;

    public ArgumentReader<?> getArguments() {
        return arguments;
    }
    public String getCommandName() {
        return commandName;
    }
}
