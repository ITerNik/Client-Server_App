package logic;


import com.fasterxml.jackson.databind.ObjectMapper;
import commands.ArgumentParser;
import commands.Command;
import constants.Messages;
import exceptions.NonUniqueIdException;
import exceptions.StartingProblemException;
import sendings.Response;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;

public class ServerService implements Service {
    ServerSocketChannel ssChannel;
    Selector selector;
    CommandBuilder builder;
    ObjectMapper mapper = new ObjectMapper();
    Manager manager;
    JsonHandler handler;
    Queue<Command> history = new ArrayDeque<>();

    public ServerService(JsonHandler handler) throws StartingProblemException, NonUniqueIdException {
        this.handler = handler;
        this.manager = new CollectionManager(handler.readCollection());
        this.builder = new CommandBuilder(manager);
    }

    public void setConnection() {

    }

    private void getQuery(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int bytesRead;
        while ((bytesRead = client.read(buffer)) > 0) {
            buffer.flip();
            byte[] bytes = new byte[bytesRead];
            buffer.get(bytes);
            baos.write(bytes);
            buffer.clear();
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        System.out.println("Received");
        key.attach(ois.readObject());
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void sendResponse(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        Response response = Response.ok("OK");
        byte[] responseBytes = response.getBytes();
        ByteBuffer buff = ByteBuffer.allocate(responseBytes.length);
        buff.put(responseBytes);
        buff.flip();
        client.write(buff);
        System.out.println("Response sent");
        key.cancel();
    }
    private void sendInfo(SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        Map<String, ArgumentParser> usersMap = (Map<String, ArgumentParser>) key.attachment();
        String json = mapper.writeValueAsString(usersMap);

        ByteBuffer buffer = ByteBuffer.wrap(json.getBytes());
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
        System.out.println("Closing server");
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
    private void logCommand(Command command) {
        if (history.size() >= 8) {
            history.remove();
        }
        history.add(command);
    }

    public void run() {
        System.out.println("Starting server...");
        try {
            ssChannel = ServerSocketChannel.open();
            ssChannel.configureBlocking(false);
            InetAddress host = InetAddress.getLocalHost();
            ssChannel.socket().bind(new InetSocketAddress(host.getHostAddress(), 9999));
            selector = Selector.open();
            ssChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Waiting for acceptance...");
            while (!Thread.currentThread().isInterrupted()) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (var iter = selectedKeys.iterator(); iter.hasNext();) {
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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }


    public static void main(String[] args) {
        try (JsonHandler handler_ = new JsonHandler(".\\Server\\src\\main\\resources\\repository.json")) {
            Service service = new ServerService(handler_);
            service.run();
        } catch (StartingProblemException | NonUniqueIdException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Messages.getMessage("warning.file_argument"));
        }
    }
}
