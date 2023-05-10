package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import constants.Messages;
import exceptions.BadParametersException;
import logic.IODevice;
import logic.JsonHandler;
import logic.Manager;

import java.io.IOException;

public class SaveCommand extends AbstractCommand {
    private final JsonHandler handler;
    public SaveCommand() {
        handler = null;
    }

    public SaveCommand(Manager manager, JsonHandler handler) {
        super(manager);
        this.handler = handler;
    }

    @Override
    public void execute() {
        try {
            handler.clear();
            handler.writeData(manager.getCollection());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new BadParametersException(Messages.getMessage("warning.write_error"));
        }
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.saved");
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.save");
    }
}
