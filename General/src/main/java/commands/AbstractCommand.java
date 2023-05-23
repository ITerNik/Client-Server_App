package commands;

import arguments.ReadableArguments;
import constants.Messages;
import logic.Manager;

/**
 * Абстрактный класс реализующий интерфейс Command и определяющий базовое поведение команд.
 * Здесь определены методы чтения и установки аргументов.
 * Все команды наследуются от этого класса и переопределяют исполнение в соответствии с требованиями.
 */
public abstract class AbstractCommand implements Command {
    protected ReadableArguments<?> readable;
    protected Manager manager;

    public AbstractCommand() {
    }
    public AbstractCommand(Manager manager) {
        this.manager = manager;
    }

    @Override
    public Command setArguments(ReadableArguments<?> arguments) {
        this.readable = arguments;
        return this;
    }

    @Override
    public ReadableArguments<?> getArguments() {
        return readable;
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.no_information");
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message_success");
    }

    @Override
    public String argumentsInfo() {
        return readable.getInfo();
    }
}
