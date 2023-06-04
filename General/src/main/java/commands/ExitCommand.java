package commands;

import arguments.ArgumentReader;
import arguments.NoReadableArguments;
import constants.Messages;

public class ExitCommand extends AbstractCommand {
    public ExitCommand() {
    }

    {
        reader = new ArgumentReader<>(new NoReadableArguments());
    }

    @Override
    public boolean execute() {
        return false;
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
