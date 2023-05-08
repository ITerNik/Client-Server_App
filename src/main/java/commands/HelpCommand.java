package commands;

import logic.IODevice;
import constants.Messages;

import java.util.HashMap;

public class HelpCommand extends AbstractCommand {
    private final HashMap<String, Command> commandList;
    private final StringBuilder report = new StringBuilder();

    public HelpCommand(IODevice io, HashMap<String, Command> commandList) {
        this.commandList = commandList;
        this.io = io;
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
