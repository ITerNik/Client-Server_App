package commands;


import constants.Messages;
import elements.Person;
import logic.Manager;

import java.util.ArrayList;

public class UpdateIdCommand extends AbstractCommand {
    private ArrayList<String> keys;
    public UpdateIdCommand() {
        setParameterName("id");
        setElement(Person.class);
        setValidator(param -> {
            try {
                int id = Integer.parseInt(param);
                keys = manager.findById(id);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(Messages.getMessage("warning.format.not_real",
                        Messages.getMessage("parameter.id")));
            }
        });
    }

    public UpdateIdCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        for (String key : keys) {
            manager.update(key, (Person) parser.getElement());
        }
    }

    @Override
    public String getName() {
        return "update";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.updated", keys);
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.update");
    }
}
