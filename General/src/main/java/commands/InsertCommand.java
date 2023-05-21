package commands;

import constants.Messages;
import elements.Person;
import logic.Manager;

import javax.xml.validation.Validator;


public class InsertCommand extends AbstractCommand {
    public InsertCommand() {
        setElement(Person.class);
        setParameterName("key");
        setValidator(param -> {
            if (manager.containsKey(param))
                throw new IllegalArgumentException(
                        Messages.getMessage("warning.format.existing_element", param));
        });
    }
    public InsertCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.put(parser.getParameter(), (Person) parser.getElement());
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.added", parser.getParameter());
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.insert");
    }
}
