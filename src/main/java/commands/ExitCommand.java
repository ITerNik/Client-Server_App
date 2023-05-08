package commands;

import constants.Messages;

import java.util.NoSuchElementException;

public class ExitCommand extends AbstractCommand {

    @Override
    public void execute() {
        throw new NoSuchElementException();
    }

    @Override
    public String getName() {
        return "exit";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.goodbye");
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.exit");
    }
}
