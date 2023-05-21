package commands;

import constants.Messages;
import logic.Manager;

public class RemoveKeyCommand extends AbstractCommand {
    public RemoveKeyCommand() {
        setParameterName("key");
        setValidator(param -> {
            if (!manager.containsKey(param))
                throw new IllegalArgumentException(
                        Messages.getMessage("warning.format.non_existing_element", param));
        });
    }
    public RemoveKeyCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        manager.remove(parser.getParameter());
    }

    @Override
    public String getName() {
        return "remove_key";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.success_delete", parser.getParameter());
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.remove_key");
    }
}
