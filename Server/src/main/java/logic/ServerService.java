package logic;

import commands.Command;
import response.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Queue;

public class ServerService implements Service{
    ServerSocketChannel socketChannel;
    SocketChannel client;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    CommandBuilder builder;
    Manager manager;
    JsonHandler handler;
    Queue<Command> history = new ArrayDeque<>();

    public ServerService(Manager manager, JsonHandler handler) {
        this.manager = manager;
        this.handler = handler;
        this.builder = new CommandBuilder(manager);
    }

    public boolean setConnection() {
        try {
            socketChannel = ServerSocketChannel.open()
                    .bind(new InetSocketAddress(54738));
            client = socketChannel.accept();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public Query getQuery() {
        try {
            ois = new ObjectInputStream(Channels.newInputStream(client));
            Query res = (Query) ois.readObject();
            if (res.getCommandName().equals("exit")) return null;
            else return res;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public void sendResponse(String message) {
        try {
            oos = new ObjectOutputStream(Channels.newOutputStream(client));
            oos.writeObject(Response.ok(message));
        } catch (IOException e) {
            //...
        }
    }

    private void logCommand(Command command) {
        if (history.size() >= 8) {
            history.remove();
        }
        history.add(command);
    }
    public void run() {
        setConnection();
        while (true) {
            Query query = getQuery();
            if (query == null) break;
            Command curr = builder.build(query);
            curr.execute();
            sendResponse(curr.getReport());
            logCommand(curr);
        }
    }
}
