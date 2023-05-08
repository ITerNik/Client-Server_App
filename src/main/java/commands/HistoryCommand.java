package commands;

import logic.IODevice;
import constants.Messages;

import java.util.Queue;

public class HistoryCommand extends AbstractCommand {
    private final Queue<Command> commandHistory;
    private StringBuilder report = new StringBuilder();

    public HistoryCommand(IODevice io, Queue<Command> commandHistory) {
        this.commandHistory = commandHistory;
        this.io = io;
    }

    @Override
    public void execute() {

        if (commandHistory.isEmpty()) {
            report = new StringBuilder(Messages.getMessage("message.no_completed"));
        } else {
            for (Command command : commandHistory) {
                report.append("\n").append(command.getName());
            }
            report = new StringBuilder(Messages.getMessage("message.completed")).append(report.toString());
        }
    }

    @Override
    public String getName() {
        return "history";
    }

    @Override
    public String getReport() {
        return report.toString();
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.history");
    }
}
