package logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.*;
import constants.Messages;
import exceptions.*;
import sendings.Query;
import sendings.Response;

import java.io.*;
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
            ReadableArguments arguments = current.getArguments();
            System.out.println("Sending query...");
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(new Query(commandName, arguments));
            oos.close();
            System.out.println("Query sent");
            return true;
        } catch (IOException e) {
            System.out.println("Query sending failed");
            return false;
        }
    }

    public void setConnection() throws BadConnectionException {
        System.out.println("Connecting to the server...");
        long start = System.currentTimeMillis();
        while(true) {
            try {
                socket = new Socket(InetAddress.getLocalHost(), 54738);
                System.out.println("Connected");
                break;
            } catch (IOException e) {
                if (System.currentTimeMillis() - start > 5000)
                    throw new BadConnectionException("Server is not responding");
            }
        }
    }

    public Response getResponse() throws BadConnectionException {
        System.out.println("Setting stream...");
        long start = System.currentTimeMillis();
        while (true) {
            try {
                ois = new ObjectInputStream(socket.getInputStream());
                break;
            } catch (IOException ignored) {
                if (System.currentTimeMillis() - start > 5000)
                    throw new BadConnectionException("Stream setting ran out of time limits");
            }
        }
        try {
            System.out.println("Stream set\nGetting response...");
            return (Response) ois.readObject();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Getting response failed");
            return null;
        }
    }
    public void run() {
        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostAddress(), 9999);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectMapper mapper = new ObjectMapper();

            StringBuilder responseBuilder = new StringBuilder();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while (inputStream.available() != 0) {
                bytesRead = inputStream.read(buffer);
                responseBuilder.append(new String(buffer, 0, bytesRead));
            }

            String jsonResponse = responseBuilder.toString();
            Map<String, ArgumentParser> commandInfo = mapper.readValue(jsonResponse, new TypeReference<Map<String, ArgumentParser>>() {});
            for (Map.Entry<String, ArgumentParser> entry: commandInfo.entrySet()) {
                System.out.println("Name: " + entry.getKey());
                System.out.println("Element: " + entry.getValue().getElement());
            }

            Query query = new Query("", null);
            OutputStream out = socket.getOutputStream();
            out.write(query.getBytes());
            out.flush();
            System.out.println("Отправлено на сервер");

            // Получение ответа от сервера
            buffer = new byte[1024];
            inputStream.read(buffer);
            //ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Response response = (Response) ois.readObject();
            System.out.println("Получено от сервера: " + response.getReport());

            // Закрытие потоков и сокета
            out.close();
            inputStream.close();
            socket.close();
            System.out.println("Соединение с сервером закрыто.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try (CliDevice cio = new CliDevice()) {
            ClientService client = new ClientService(cio);
            client.run();
        }
    }
}
