package logic;

import arguments.ArgumentReader;
import commands.*;
import sendings.Query;

import java.util.*;

public class CommandBuilder {
    private final HashMap<String, Command> commandList = new HashMap<>();
    private final HashMap<String, ArgumentReader<?>> commandInfo = new HashMap<>();
    private ArrayList<String> fileLog;
    private final Queue<Command> commandLog = new ArrayDeque<>() {
        @Override
        public boolean add(Command command) {
            if (size() >= 8) remove();
            return super.add(command);
        }
    };
    private final Manager manager;

    public CommandBuilder(Manager manager) {
        this.manager = manager;
        initialize();
    }

    public CommandBuilder(Manager manager, ArrayList<String> fileHistory) {
        this.manager = manager;
        this.fileLog = fileHistory;
        initialize();
    }

    public void addCommand(Command... commands) {
        for (Command command : commands) {
            String commandName = command.getName();
            commandList.put(commandName, command);
            commandInfo.put(commandName, command.getReader());
        }
    }

    private void initialize() {
        addCommand(new ExitCommand(), new ClearCommand(), new TestCommand(),
                new InfoCommand(manager), new ShowCommand(manager), new InsertCommand(manager),
                new RemoveKeyCommand(manager), new UpdateIdCommand(), new SaveCommand(),
                new RemoveLowerCommand(manager), new HistoryCommand(commandLog, manager), new RemoveGreaterCommand(manager),
                new HelpCommand(commandList, manager), new CountByWeightCommand(manager), new GreaterLocationCommand(manager),
                new FilterByLocationCommand(manager));
    }

    public Command build(Query query) {
        return commandList.get(query.getCommandName()).setArguments(query.getArguments());
    }
    public Command get(String name) {
        return commandList.getOrDefault(name, new InfoCommand(manager));
    }

    public boolean logFile(String fileName) {
        if (!fileLog.contains(fileName)) {
            fileLog.add(fileName);
            return true;
        }
        return false;
    }
    public void logCommand(Command command) {
        commandLog.add(command);
    }

    public HashMap<String, ArgumentReader<?>> getArguments() {
        return commandInfo;
    }

}