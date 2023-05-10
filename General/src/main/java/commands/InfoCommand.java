package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

public class InfoCommand extends AbstractCommand {
    private String report;
    public InfoCommand() {
    }

    public InfoCommand(Manager manager) {
        super(manager);
    }

    @Override
    public void execute() {
        report = manager.getInfo();
    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getReport() {
        return report;
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.info");
    }
}
