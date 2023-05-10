package commands;

import constants.Messages;
import elements.Location;
import logic.IODevice;
import logic.Manager;

public class GreaterLocationCommand extends AbstractCommand {
    private int count;
    public GreaterLocationCommand() {
        setElements(Location.class, 1);
    }

    public GreaterLocationCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        count = manager.countGreaterThanLocation((Location) parser.getElements()[0]);
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.greater_location");
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.count", count);
    }

    @Override
    public String getName() {
        return "count_greater_than_location";
    }
}
