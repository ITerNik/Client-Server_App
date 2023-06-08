package arguments;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import constants.Messages;
import exceptions.BadParametersException;
import logic.FileDevice;
import logic.IODevice;
import sendings.Query;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class FileArguments implements Readable {
    @JsonIgnore
    private HashMap<String, ArgumentReader> commandInfo = new HashMap<>();

    public FileArguments() {
    }

    public FileArguments(HashMap<String, ArgumentReader> commandInfo) {
        this.commandInfo = commandInfo;
    }

    @Override
    public String read(IODevice from) throws JsonProcessingException {
        Path file = Paths.get(from.read()); //TODO: Check if files are cyclic
        ArrayList<String> commands = new ArrayList<>();
        if (Files.notExists(file)) throw new BadParametersException(
                Messages.getMessage("warning.format.file_not_found", file.getFileName()));
        try {
            FileDevice input = new FileDevice(file);
            while (input.hasNextLine()) {
                String commandName = input.read();
                ArgumentReader current = commandInfo.get(commandName);
                current.read(input);
                Query query = new Query(commandName, current);
                commands.add(mapper.writerFor(Query.class).writeValueAsString(query));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mapper.writerFor(new TypeReference<ArrayList<String>>() {}).writeValueAsString(commands);
    }

    public void setCommandInfo(HashMap<String, ArgumentReader> commandInfo) {
        this.commandInfo = commandInfo;
    }

    public HashMap<String, ArgumentReader> getCommandInfo() {
        return commandInfo;
    }
}
