package commands;

import java.util.AbstractMap.SimpleEntry;

import constants.Messages;
import elements.Person;
import logic.IODevice;
import logic.Manager;


public class InsertCommand extends AbstractCommand {
    public InsertCommand() {
    }
    public InsertCommand(Manager manager) {
        super(manager);
    }

    {
        readable = new ReadableArguments<SimpleEntry<String, Person>>() {
            @Override
            public void read(IODevice io) {
                String key = io.read();
                if (manager.containsKey(key))
                    throw new IllegalArgumentException(
                            Messages.getMessage("warning.format.existing_element", key));
                arguments = new SimpleEntry<>(key, io.readElement(Person.class));
            }
        };
    }

    @Override
    public boolean execute() {
        SimpleEntry<String, Person> entry = (SimpleEntry<String, Person>) readable.getArguments();
        manager.put(entry.getKey(), entry.getValue());
        return true;
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getReport() {
        SimpleEntry<String, Person> entry = (SimpleEntry<String, Person>) readable.getArguments();
        return Messages.getMessage("message.format.added", entry.getKey());
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.insert");
    }
}
