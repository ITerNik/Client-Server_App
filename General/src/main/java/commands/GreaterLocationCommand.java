package commands;

import arguments.ArgumentReader;
import arguments.LocationArguments;
import constants.Messages;
import elements.Location;
import logic.Manager;

public class GreaterLocationCommand extends AbstractCommand {
    private int count;
    public GreaterLocationCommand() {
    }

    public GreaterLocationCommand(Manager manager) {
        super(manager);
    }
    {
        reader = new ArgumentReader<>(new LocationArguments());
    }

    @Override
    public boolean execute() {
        count = manager.countGreaterThanLocation((Location) reader.getArgument());
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
