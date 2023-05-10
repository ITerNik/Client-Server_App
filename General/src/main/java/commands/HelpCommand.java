package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

import java.util.HashMap;

public class HelpCommand extends AbstractCommand {
    private final HashMap<String, Command> commandList;
    private final StringBuilder report = new StringBuilder();
    public HelpCommand() {
        commandList = null;
    }

    public HelpCommand(HashMap<String, Command> commandList, Manager manager) {
        super(manager);
        this.commandList = commandList;

    }

    @Override
    public void execute() {
        for (Command command : commandList.values()) {
            report.append(String.format("%s%s: %s\n", command.getName(), command.argumentsInfo(), command.getInfo()));
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getReport() {
        return report.toString();
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.help");
    }
}
