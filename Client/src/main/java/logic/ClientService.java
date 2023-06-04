package logic;

import arguments.ArgumentReader;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.Constants;
import constants.Messages;
import exceptions.*;
import sendings.Query;
import sendings.Response;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.util.*;

public class ClientService implements Service {
    private final CliDevice cio;
    private InputStream inputStream;
    private OutputStream outputStream;
    private HashMap<String, ArgumentReader<?>> commandInfo;

    ObjectMapper mapper = new ObjectMapper();
    {
        mapper.disable(JsonParser.Feature.AUTO_CLOSE_SOURCE);
    }

    public ClientService(CliDevice cio) {
        this.cio = cio;
        initConnection();
    }

    public void sendQuery() {
        while (true) {
            String commandName = cio.read();
            ArgumentReader<?> arguments = commandInfo.get(commandName);
            if (arguments == null) throw new NoSuchCommandException(
                    Messages.getMessage("warning.format.no_such_command", commandName));
            try {
                arguments.read(cio);

                Query query = new Query(commandName, arguments);
                outputStream.write(mapper.writeValueAsBytes(query));
                outputStream.flush();

                System.out.println("Отправлено на сервер");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void initConnection() {
        try {
            Socket socket = new Socket(Constants.CLIENT_HOST, Constants.PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            commandInfo = mapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (ConnectException e) {
            System.out.println("Connection failed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Response getResponse() {
        try {
            return mapper.readValue(inputStream, Response.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.error("");
    }

    public void run() {
        try {
            sendQuery();
            getResponse();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

    }

    public void closeConnection() {
        try {
            inputStream.close();
            outputStream.close();
            System.out.println("Соединение с сервером закрыто");
        } catch (IOException e) {
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
