package commands;

import constants.Messages;
import elements.Person;
import logic.IODevice;
import logic.Manager;

import java.util.ArrayList;

public class RemoveLowerCommand extends AbstractCommand {
    private ArrayList<String> removed;
    public RemoveLowerCommand() {
        setElement(Person.class);
    }
    public RemoveLowerCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        removed = manager.removeLower((Person) parser.getElement());
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
