package logic;

import commands.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CommandBuilder {
    private final HashMap<String, Command> commandList = new HashMap<>();
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
            commandList.put(command.getName(), command);
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
}