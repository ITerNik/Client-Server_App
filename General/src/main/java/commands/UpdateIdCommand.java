package commands;


import constants.Messages;
import elements.Person;
import logic.IODevice;
import logic.Manager;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

public class UpdateIdCommand extends AbstractCommand {
    public UpdateIdCommand() {
    }

    public UpdateIdCommand(Manager manager) {
        super(manager);
    }
    {
        readable = new ReadableArguments<SimpleEntry<ArrayList<String>, Person>>() {
            @Override
            public void read(IODevice io) {
                try {
                    int id = Integer.parseInt(io.read());
                    arguments = new SimpleEntry<>(manager.findById(id), io.readElement(Person.class));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException(Messages.getMessage("warning.format.not_real",
                            Messages.getMessage("parameter.id")));
                }
            }
        };
    }

    @Override
    public boolean execute() {
        for (String key : ((SimpleEntry<ArrayList<String>, Person>) readable.getArguments()).getKey()) {
            manager.update(key, (Person) readable.getArguments());
        }
        return true;
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.updated", ((SimpleEntry) readable.getArguments()).getKey());
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.update");
    }
}
