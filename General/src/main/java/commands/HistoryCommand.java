package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

import java.util.Queue;

public class HistoryCommand extends AbstractCommand {
    private final Queue<Command> commandHistory;
    private StringBuilder report = new StringBuilder();
    public HistoryCommand() {
        this.commandHistory =  null;
    }

    public HistoryCommand(Queue<Command> commandHistory, Manager manager) {
        super(manager);
        this.commandHistory = commandHistory;
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
