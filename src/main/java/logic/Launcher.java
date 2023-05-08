package logic;

import exceptions.NonUniqueIdException;
import exceptions.StartingProblemException;
import constants.Messages;


public class Launcher {

    public static void main(String[] args) {
        try (JsonHandler handler = new JsonHandler("test.json");
             CliDevice cio = new CliDevice()) {
            Manager manager = new CollectionManager(handler.readCollection());
            Service app = new ConsoleService(cio, manager, handler);
            app.start();
        } catch (StartingProblemException | NonUniqueIdException e) {
            System.out.println(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(Messages.getMessage("warning.file_argument"));
        }
    }
}
