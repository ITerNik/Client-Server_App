package logic;

import commands.*;
import sendings.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandBuilder {
    private final HashMap<String, Command> commandList = new HashMap<>();
    private final HashMap<String, ArgumentParser> commandInfo = new HashMap<>();
    private ArrayList<String> fileHistory;
    private final Manager manager;

    public CommandBuilder(Manager manager) {
        this.manager = manager;
        initialize();
    }

    public CommandBuilder(Manager manager, ArrayList<String> fileHistory) {
        this.manager = manager;
        this.fileHistory = fileHistory;
        initialize();
    }

    public void addCommand(Command... commands) {
        for (Command command : commands) {
            String commandName = command.getName();
            commandList.put(commandName, command);
            commandInfo.put(commandName, command.getParser());
        }
    }

    private void initialize() {
        addCommand(new ExitCommand(), new ClearCommand(), new TestCommand(),
                new InfoCommand(), new ShowCommand(), new InsertCommand(manager),
                new RemoveKeyCommand(), new UpdateIdCommand(), new SaveCommand(),
                new RemoveLowerCommand(), new HistoryCommand(), new RemoveGreaterCommand(),
                new HelpCommand(commandList, manager), new CountByWeightCommand(), new GreaterLocationCommand(),
                new FilterByLocationCommand());
    }

    public Command build(Query query) {
        return commandList.get(query.getCommandName()).setArguments(query.getParser());
    }

    public boolean addToFileHistory(String fileName) {
        if (!fileHistory.contains(fileName)) {
            fileHistory.add(fileName);
            return true;
        }
        return false;
    }
    public HashMap<String, ArgumentParser> getArguments() {
        return commandInfo;
    }
}