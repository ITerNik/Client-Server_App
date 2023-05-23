package commands;

import arguments.ReadableArguments;
import constants.Messages;
import elements.Location;
import elements.Person;
import logic.IODevice;
import logic.Manager;

import java.util.ArrayList;

public class FilterByLocationCommand extends AbstractCommand {

    private ArrayList<Person> selected;
    public FilterByLocationCommand() {
    }
    public FilterByLocationCommand(Manager manager) {
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
        selected = manager.filterByLocation((Location) readable.getArguments());
        return true;
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.filter_by_location");
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.found", selected);
    }

    @Override
    public String getName() {
        return "filter_by_location";
    }
}
