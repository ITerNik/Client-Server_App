package commands;

import constants.Messages;
import elements.Person;
import logic.IODevice;
import logic.Manager;


public class InsertCommand extends AbstractCommand {
    public InsertCommand() {
        setElements(Person.class, 1);
        setParameterNames("key");
        setValidator(param -> {
            if (manager.containsKey(param[0]))
                throw new IllegalArgumentException(
                        Messages.getMessage("warning.format.existing_element", param[0]));
        });
    }
    public InsertCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.put(parser.getParameters()[0], (Person) parser.getElements()[0]);
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.added", parser.getParameters()[0]);
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.insert");
    }
}
