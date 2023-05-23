package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

import java.util.ArrayList;

public class RemoveGreaterCommand extends AbstractCommand {
    private ArrayList<String> removed;
    public RemoveGreaterCommand() {
        //TODO Deprecate setParameterName("key");
    }
    public RemoveGreaterCommand(Manager manager) {
        super(manager);
    }
    {
        readable = new ReadableArguments<String>() {
            @Override
            public void read(IODevice io) {
                arguments = io.read();
            }
        };
    }

    @Override
    public boolean execute() {
        removed = manager.removeGreater((String) readable.getArguments());
        return true;
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
