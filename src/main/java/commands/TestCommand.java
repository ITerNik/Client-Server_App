package commands;

import logic.IODevice;
import logic.Manager;
import constants.Messages;


public class TestCommand extends AbstractCommand {
    public TestCommand(IODevice io, Manager manager) {
        super(io, manager);
    }


    @Override
    public void execute() {
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.test");
    }
}
