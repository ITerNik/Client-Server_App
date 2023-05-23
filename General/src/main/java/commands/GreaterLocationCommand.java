package commands;

import arguments.ReadableArguments;
import constants.Messages;
import elements.Location;
import logic.IODevice;
import logic.Manager;

public class GreaterLocationCommand extends AbstractCommand {
    private int count;
    public GreaterLocationCommand() {
    }

    public GreaterLocationCommand(Manager manager) {
        super(manager);
    }
    {
        readable = new ReadableArguments<Location>() {
            @Override
            public void read(IODevice io) {
                arguments = io.readElement(Location.class);
            }
        };
    }

    @Override
    public boolean execute() {
        count = manager.countGreaterThanLocation((Location) readable.getArguments());
        return true;
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
