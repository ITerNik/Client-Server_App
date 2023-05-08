package commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import logic.IODevice;
import logic.JsonHandler;
import logic.Manager;
import constants.Messages;

import java.io.IOException;

public class SaveCommand extends AbstractCommand {
    private final JsonHandler handler;

    public SaveCommand(IODevice io, Manager manager, JsonHandler handler) {
        super(io, manager);
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
            io.write("warning.write_error");
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
