package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;


public class TestCommand extends AbstractCommand {
    public TestCommand() {

    }
    public TestCommand(Manager manager) {
        this();
        this.manager = manager;
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
