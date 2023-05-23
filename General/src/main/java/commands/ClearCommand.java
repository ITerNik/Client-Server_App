package commands;

import arguments.NoReadableArguments;
import constants.Messages;
import logic.Manager;

public class ClearCommand extends AbstractCommand {
    public ClearCommand() {
    }
    public ClearCommand(Manager manager) {
        super(manager);
    }
    {
        readable = new NoReadableArguments();
    }
    @Override
    public boolean execute() {
        manager.clear();
        return true;
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
