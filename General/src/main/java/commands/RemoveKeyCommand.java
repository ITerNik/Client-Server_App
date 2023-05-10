package commands;

import constants.Messages;
import logic.IODevice;
import logic.Manager;

public class RemoveKeyCommand extends AbstractCommand {
    public RemoveKeyCommand() {
        setParameterNames("key");
        setValidator(param -> {
            if (!manager.containsKey(param[0]))
                throw new IllegalArgumentException(
                        Messages.getMessage("warning.format.non_existing_element", param[0]));
        });
    }
    public RemoveKeyCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.remove(parser.getParameters()[0]);
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.success_delete", parser.getParameters()[0]);
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.remove_key");
    }
}
