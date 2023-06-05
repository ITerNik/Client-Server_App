package commands;


import arguments.ArgumentReader;
import arguments.IntegerPersonArguments;
import constants.Messages;
import elements.Person;
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
        reader = new ArgumentReader<>(new IntegerPersonArguments());
    }

    @Override
    public void execute() {
        SimpleEntry<?, ?> entry = (SimpleEntry<?, ?>) reader.getArgument();
        ArrayList<String> keys = manager.findById((int) entry.getKey());
        for (String key : keys) {
            manager.update(key, (Person) reader.getArgument());
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.updated", ((SimpleEntry<?, ?>) reader.getArgument()).getKey());
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.update");
    }
}
