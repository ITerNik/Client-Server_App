package commands;

import exceptions.BadParametersException;
import logic.IODevice;
import logic.Manager;
import constants.Messages;

/**
 * Абстрактный класс реализующий интерфейс Command и определяющий базовое поведение команд.
 * Здесь определены методы чтения и установки аргументов.
 * Все команды наследуются от этого класса и переопределяют исполнение в соответствии с требованиями.
 */
public abstract class AbstractCommand implements Command {
    protected Manager manager;
    protected IODevice io;
    protected String[] parameters;
    protected Object[] elements;
    protected Class type;

    public AbstractCommand() {
    }

    public AbstractCommand(IODevice io, Manager manager) {
        this.manager = manager;
        this.io = io;
    }

    /**
     * Проверяет аргументы из командной строки на соответствие формату
     * и выбрасывает исключение для обработки выше в случае некорректного ввода.
     * Переопределить при необходимости
     *
     * @param param строка идущая после названия команды, разбитая на токены по пробельным символам
     * @throws BadParametersException если аргумент не соответствует формату
     */
    protected void checkArguments(String[] param) throws BadParametersException {
    }

    /**
     * Устанавливает количество аргументов, требуемых команде для последующей проверки
     * с помощью {@link #checkArguments} и считывания {@link #parseArguments}
     *
     * @param names набор строк которые будут выводиться при вызове команды help
     * @see #argumentsInfo()
     */
    protected void setParameterNames(String... names) {
        parameters = names;
    }

    /**
     * Устанавливает количество экземпляров, требуемых команде для считывания и исполнения.
     * Задает количество скобок {element} выводимых help
     *
     * @param number количество считываемых элементов коллекции
     * @see #argumentsInfo()
     */
    protected void setElements(Class type, int number) {
        this.elements = new Object[number];
        this.type = type;
    }

    @Override
    public Command parseArguments(String[] param) throws BadParametersException {
        if (parameters != null) {
            if (param.length == 0) throw new BadParametersException(Messages.getMessage("warning.empty_argument"));
            checkArguments(param);
            for (int i = 0; i < Math.max(parameters.length, param.length); ++i) {
                try {
                    parameters[i] = param[i];
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new BadParametersException(Messages.getMessage("warning.format.number_of_arguments", parameters.length));
                }
            }
        } else if (param.length != 0)
            throw new BadParametersException(Messages.getMessage("warning.needless_argument"));

        if (elements != null) {
            for (int i = 0; i < elements.length; ++i) {
                elements[i] = io.readElement(type);
            }
        }
        return this;
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
        StringBuilder res = new StringBuilder();
        if (parameters != null) {
            for (String param : parameters) {
                res.append(" ").append(param);
            }
        }
        if (elements != null) {
            res.append(String.format(" {%s}", type.getSimpleName()).repeat(elements.length));
        }
        return res.toString();
    }
}
