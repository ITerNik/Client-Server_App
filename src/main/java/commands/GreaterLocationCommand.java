package commands;

import elements.Location;
import logic.IODevice;
import logic.Manager;
import constants.Messages;

public class GreaterLocationCommand extends AbstractCommand {
    private int count;

    public GreaterLocationCommand(IODevice io, Manager manager) {
        super(io, manager);
        setElements(Location.class, 1);
    }



    @Override
    public void execute() {
        count = manager.countGreaterThanLocation((Location) elements[0]);
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
