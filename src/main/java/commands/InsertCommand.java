package commands;

import elements.Person;
import logic.IODevice;
import logic.Manager;
import constants.Messages;


public class InsertCommand extends AbstractCommand {
    public InsertCommand(IODevice io, Manager manager) {
        super(io, manager);
        setElements(Person.class, 1);
        setParameterNames("key");
    }

    @Override
    protected void checkArguments(String[] param) throws IllegalArgumentException {
        if (manager.containsKey(param[0]))
            throw new IllegalArgumentException(Messages.getMessage("warning.format.existing_element", param[0]));
    }

    @Override
    public void execute() {
        manager.put(parameters[0], (Person) elements[0]);
    }

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.added", parameters[0]);
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.insert");
    }
}
