package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

import java.util.ArrayList;

public class RemoveGreaterCommand extends AbstractCommand {
    private ArrayList<String> removed;
    public RemoveGreaterCommand() {
        setParameterName("key");
    }
    public RemoveGreaterCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        removed = manager.removeGreater(parser.getParameter());
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
