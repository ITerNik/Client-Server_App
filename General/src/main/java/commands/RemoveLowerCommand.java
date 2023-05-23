package commands;

import arguments.ReadableArguments;
import constants.Messages;
import elements.Person;
import logic.IODevice;
import logic.Manager;

import java.util.ArrayList;

public class RemoveLowerCommand extends AbstractCommand {
    private ArrayList<String> removed;
    public RemoveLowerCommand() {
    }
    public RemoveLowerCommand(Manager manager) {
        super(manager);
    }
    {
        readable = new ReadableArguments<Person>() {
            @Override
            public void read(IODevice io) {
                arguments = io.readElement(Person.class);
            }
        };
    }

    @Override
    public boolean execute() {
        removed = manager.removeLower((Person) readable.getArguments());
        return true;
    }

    @Override
    public String getName() {
        return "remove_lower";
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
        return Messages.getMessage("command.remove_lower");
    }
}
