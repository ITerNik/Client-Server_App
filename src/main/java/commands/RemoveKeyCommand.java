package commands;

import logic.IODevice;
import logic.Manager;
import constants.Messages;

public class RemoveKeyCommand extends AbstractCommand {
    public RemoveKeyCommand(IODevice io, Manager manager) {
        super(io, manager);
        setParameterNames("key");
    }

    @Override
    protected void checkArguments(String[] param) throws IllegalArgumentException {
        if (!manager.containsKey(param[0]))
            throw new IllegalArgumentException(Messages.getMessage("warning.format.non_existing_element", param[0]));
    }

    @Override
    public void execute() {
        manager.remove(parameters[0]);
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.success_delete", parameters[0]);
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.remove_key");
    }
}
