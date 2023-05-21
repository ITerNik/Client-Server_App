package commands;

import constants.Messages;
import logic.Manager;

import java.io.Serializable;

/**
 * Абстрактный класс реализующий интерфейс Command и определяющий базовое поведение команд.
 * Здесь определены методы чтения и установки аргументов.
 * Все команды наследуются от этого класса и переопределяют исполнение в соответствии с требованиями.
 */
public abstract class AbstractCommand implements Command {
    protected ArgumentParser parser = new ArgumentParser();
    protected Manager manager;

    public AbstractCommand() {
    }
    public AbstractCommand(Manager manager) {
        this.manager = manager;
    }

    @Override
    public Command setArguments(ArgumentParser parser) {
        this.parser = parser;
        return this;
    }

    /**
     * Проверяет аргументы из командной строки на соответствие формату
     * и выбрасывает исключение для обработки выше в случае некорректного ввода.
     * Переопределить при необходимости
     */
    protected void setValidator(ValidChecker checker)  {
        parser.setValidator(checker);
    }

    /**
     * Устанавливает количество аргументов, требуемых команде для последующей проверки
     * с помощью {@link ValidChecker} и считывания {@link ValidChecker}
     *
     * @param name набор строк которые будут выводиться при вызове команды help
     * @see #argumentsInfo()
     */
    protected void setParameterName(String name) {
        parser.setParameter(name);
    }

    /**
     * Устанавливает количество экземпляров, требуемых команде для считывания и исполнения.
     * Задает количество скобок {element} выводимых help
     *
     * @see #argumentsInfo()
     */
    protected void setElement(Class<?> type) {
        parser.setElement(type);
    }

    public ArgumentParser getParser() {
        return parser;
    }

    @Override
    public String getInfo() {
        return "No information";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message_success");
    }

    @Override
    public String argumentsInfo() {
        String parameter = parser.getParameter();
        return " " + parameter + " {" + parser.getElement().getClass().getSimpleName() + "}";
    }
}
