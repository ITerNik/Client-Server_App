package commands;

import java.util.AbstractMap.SimpleEntry;

import arguments.ArgumentReader;
import arguments.StringPersonArguments;
import constants.Messages;
import elements.Person;
import logic.Manager;


public class InsertCommand extends AbstractCommand {
    public InsertCommand() {
    }
    public InsertCommand(Manager manager) {
        super(manager);
    }

    {
        reader = new ArgumentReader<>(new StringPersonArguments());
    }

    @Override
    public void execute() {
        SimpleEntry<String, Person> entry = (SimpleEntry<String, Person>) reader.getArgument();
        manager.put(entry.getKey(), entry.getValue());
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.added", ((SimpleEntry<?, ?>) reader.getArgument()).getKey());
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.insert");
    }
}
