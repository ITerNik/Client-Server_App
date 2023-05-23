package commands;

import constants.Messages;
import logic.Manager;


public class TestCommand extends AbstractCommand {
    public TestCommand() {

    }
    public TestCommand(Manager manager) {
        super(manager);
    }


    @Override
    public boolean execute() {
        return true;
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
