package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

public class RemoveKeyCommand extends AbstractCommand {
    public RemoveKeyCommand() {
    }
    public RemoveKeyCommand(Manager manager) {
        super(manager);
    }
    {
        readable = new ReadableArguments<String>() {
            @Override
            public void read(IODevice io) {
                arguments = io.read();
                if (!manager.containsKey(arguments))
                    throw new IllegalArgumentException(
                            Messages.getMessage("warning.format.non_existing_element", arguments));
            }
        };
    }


    @Override
    public boolean execute() {
        manager.remove((String) readable.getArguments());
        return true;
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.success_delete", readable.getArguments());
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.remove_key");
    }
}
