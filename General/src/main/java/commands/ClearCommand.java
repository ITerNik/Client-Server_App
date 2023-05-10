package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

public class ClearCommand extends AbstractCommand {
    public ClearCommand() {
    }
    public ClearCommand(Manager manager) {
        super(manager);
    }
    @Override
    public void execute() {
        manager.clear();
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.cleared");
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.clear");
    }
}
