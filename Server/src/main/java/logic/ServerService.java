package logic;


import arguments.ArgumentReader;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commands.Command;
import constants.Constants;
import constants.Messages;
import exceptions.CloseConnectionSignal;
import exceptions.NoSuchCommandException;
import exceptions.NonUniqueIdException;
import exceptions.StartingProblemException;
import sendings.Query;
import sendings.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class ServerService implements Service {
    private ServerSocketChannel ssChannel;
    private Selector selector;
    private final CommandBuilder builder;

    private final Scanner input = new Scanner(System.in);

    private final ObjectMapper mapper = new ObjectMapper();
    private Manager manager;

    public ServerService(String fileName) throws StartingProblemException, NonUniqueIdException {
        this.manager = new CollectionManager(fileName);
        this.builder = new CommandBuilder(manager);
        initConnection();
    }

    public void initConnection() {
        System.out.println(Messages.getMessage("message.initializing"));
        try {
            ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            ssChannel.socket().bind(new InetSocketAddress(Constants.SERVER_HOST, Constants.PORT));
            selector = Selector.open();
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getQuery(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(Constants.BUFFER_SIZE);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = client.read(buffer)) > 0) {
            buffer.flip();
            byte[] bytes = new byte[bytesRead];
            buffer.get(bytes, 0, bytesRead);
            out.write(bytes);
        }
        if (bytesRead == -1) {
            return;
        }
        byte[] queryBytes = out.toByteArray();

        Query query = mapper.readValue(queryBytes, new TypeReference<>() {});
        System.out.println("Received");
        Response response = handleQuery(query);

        key.attach(response);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private Response handleQuery(Query query) {
        Command command = builder.build(query);
        command.execute();
        builder.logCommand(command);
        return new Response(command.getReport());
    }

    private void sendResponse(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        Response response = (Response) key.attachment();
        ByteBuffer responseBytes = ByteBuffer.wrap(mapper.writeValueAsBytes(response));
        client.write(responseBytes);

        System.out.println("Response sent");
        key.interestOps(SelectionKey.OP_READ);
    }

    private void sendInfo(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        Map<String, ArgumentReader<?>> argumentsInfo = (Map<String, ArgumentReader<?>>) key.attachment();
        byte[] mapBytes = mapper.writeValueAsBytes(argumentsInfo);

        ByteBuffer buffer = ByteBuffer.wrap(mapBytes);
        client.write(buffer);

        System.out.println("Info sent");
        key.interestOps(SelectionKey.OP_READ);
    }

    private void acceptConnection(SelectionKey key) throws IOException {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = serverChannel.accept();
        clientChannel.configureBlocking(false);

        clientChannel.register(selector, SelectionKey.OP_WRITE, builder.getArguments());
    }

    private void closeConnection() {
        System.out.println("Соединение закрыто");
        if (selector != null) {
            try {
                selector.close();
                ssChannel.socket().close();
                ssChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                checkInput();
                selector.select(Constants.TIMEOUT);
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (var iter = selectedKeys.iterator(); iter.hasNext(); ) {
                    SelectionKey key = iter.next();
                    iter.remove();

                    if (!key.isValid()) continue;
                    if (key.isAcceptable()) acceptConnection(key);
                    if (key.isReadable()) getQuery(key);
                    if (key.isWritable()) {
                        if (key.attachment() instanceof HashMap) sendInfo(key);
                        else sendResponse(key);
                    }
                }
            }
        } catch (CloseConnectionSignal ignored) {
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    private void checkInput() throws IOException {
        while (System.in.available() > 0) {
            try {
                Command command = builder.build(input.next());
                command.execute();
                System.out.println(command.getReport());
            } catch (NoSuchCommandException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void main(String[] args) {
        try {
            Service service = new ServerService(".\\Server\\src\\main\\resources\\repository.json");
            service.run();
        } catch (StartingProblemException | NonUniqueIdException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Messages.getMessage("warning.file_argument"));
        }
    }
}
