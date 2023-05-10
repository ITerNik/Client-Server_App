package logic;

import constants.Messages;
import exceptions.NonUniqueIdException;
import exceptions.StartingProblemException;


public class Launcher {

    public static void main(String[] args) {
        try (JsonHandler handler = new JsonHandler("test.json");
             CliDevice cio = new CliDevice()) {
            Manager manager = new CollectionManager(handler.readCollection());
            Service server = new ServerService(manager, handler);
            server.run();
            Service client = new ClientService(cio);
            client.run();
        } catch (StartingProblemException | NonUniqueIdException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Messages.getMessage("warning.file_argument"));
        }
    }
}
