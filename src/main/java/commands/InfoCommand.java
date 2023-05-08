package commands;

import logic.IODevice;
import logic.Manager;
import constants.Messages;

public class InfoCommand extends AbstractCommand {
    private String report;

    public InfoCommand(IODevice io, Manager manager) {
        super(io, manager);
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
