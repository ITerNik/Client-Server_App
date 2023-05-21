package commands;

import constants.Messages;
import elements.Location;
import elements.Person;
import logic.IODevice;
import logic.Manager;

import java.util.ArrayList;

public class FilterByLocationCommand extends AbstractCommand {

    private ArrayList<Person> selected;
    public FilterByLocationCommand() {
        setElement(Location.class);
    }
    public FilterByLocationCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        selected = manager.filterByLocation((Location) parser.getElement());
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
