package logic;

import commands.*;
import constants.Messages;
import exceptions.BadParametersException;
import exceptions.NoSuchCommandException;
import response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class ClientService implements Service {
    private final CliDevice cio;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ClientService(CliDevice commandIO) {
        this.cio = commandIO;
    }

    HashMap<String, Command> commandInfo = new HashMap<>();

    public void addCommand(Command... commands) {
        for (Command command: commands) {
            commandInfo.put(command.getName(), command);
        }
    }

    {
        addCommand(new ExitCommand(), new ClearCommand(), new TestCommand(),
        new InfoCommand(), new ShowCommand(), new InsertCommand(),
        new RemoveKeyCommand(), new UpdateIdCommand(), new SaveCommand(),
        new RemoveLowerCommand(), new HistoryCommand(), new RemoveGreaterCommand(),
        new HelpCommand(), new CountByWeightCommand(), new GreaterLocationCommand(),
                new FilterByLocationCommand());
    }

    public boolean sendQuery() {
        try {
            cio.write(Messages.getMessage("input.command"));
            String commandName = cio.read();
            Command current = commandInfo.get(commandName);
            if (current == null) throw new NoSuchCommandException(
                    Messages.getMessage("warning.format.no_such_command", commandName));
            ArgumentParser parser = current.getParser().parseFrom(cio);
            oos.writeObject(new Query(commandName, parser));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean setConnection() {
        try {
            socket = new Socket(InetAddress.getLocalHost(), 54738);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    public Response getResponse() {
        try {
            return (Response) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
    public void run() {
        setConnection();
        cio.write(Messages.getMessage("message.welcome"));
        while (true) {
            try {
                sendQuery();
                cio.write(getResponse().getReport());
            } catch (NoSuchCommandException | BadParametersException | IllegalArgumentException e) {
                cio.write(e.getMessage() + "\n");
            } catch (NoSuchElementException e) {
                cio.write(Messages.getMessage("message.goodbye"));
                break;
            }
        }
    }
}
