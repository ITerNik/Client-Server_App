package commands;

import exceptions.BadParametersException;
import logic.Query;

public interface Command {
    /**
     * Исполнение команды. Переопределяется в классах наследниках
     */
    void execute();
    ArgumentParser getParser();
    Command setArguments(ArgumentParser parser);

    /**
     * Возвращает название для добавления в список команд и вывода в help
     * @see HelpCommand
     * @return название команды в консоли
     */
    String getName();
    /**
     * Возвращает описание функционала команды для вывода в help.
     * Необходимо переопределить в наследниках
     * @return описание команды
     * @see HelpCommand
     */
    String getInfo();

    /**
     * Возвращает результат выполнения команды в строковом представлении.
     * Необходимо переопределить в классах наследниках
     * @return результат выполнения команды
     */
    String getReport();

    /**
     * Возвращает названия аргументов и количество необходимых экземпляров {element} коллекции для вывода в help
     * @see HelpCommand
     * @return названия аргументов и количество экземпляров в строчном представлении
     */
    String argumentsInfo();
}
