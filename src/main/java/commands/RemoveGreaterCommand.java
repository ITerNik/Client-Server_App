package commands;

import logic.IODevice;
import logic.Manager;
import constants.Messages;

import java.util.ArrayList;

public class RemoveGreaterCommand extends AbstractCommand {
    private ArrayList<String> removed;
    public RemoveGreaterCommand(IODevice io, Manager manager) {
        super(io, manager);
        setParameterNames("key");
    }

    @Override
    public void execute() {
        removed = manager.removeGreater(parameters[0]);
    }

    @Override
    public String getName() {
        return "remove_greater_key";
    }

    @Override
    public String getReport() {
        if (removed.isEmpty()) {
            return Messages.getMessage("message.nothing_deleted");
        } else {
            return Messages.getMessage("message.format.deleted", removed);
        }
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.remove_greater");
    }
}
