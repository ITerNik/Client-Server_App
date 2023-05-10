package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

public class ShowCommand extends AbstractCommand {
    private String report;
    public ShowCommand() {
    }
    public ShowCommand( Manager manager) {
        super (manager);
    }

    @Override
    public void execute() {
        report = manager.toString();
    }

    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getReport() {
        return report;
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.show");
    }
}
