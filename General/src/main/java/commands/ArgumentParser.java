package commands;

import constants.Messages;
import exceptions.BadParametersException;
import logic.IODevice;

import java.io.Serializable;

public class ArgumentParser implements Serializable {
    private String parameter;
    transient private Object element;
    Class<?> type;
    private ValidChecker checker;

    ArgumentParser() {
    }

    public ArgumentParser parseFrom(IODevice io) {
        String line = io.readLine().strip();
        if (parameter != null) {
            if (line.isBlank()) throw new BadParametersException(Messages.getMessage("warning.empty_argument"));
            if (checker != null) checker.check(line);
            parameter = line;
        } else if (!line.isBlank())
            throw new BadParametersException(Messages.getMessage("warning.needless_argument"));

        if (element != null) {
            element = io.readElement(type); //TODO: Заменить на Builder/Supplier
        }
        return this;
    }


    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public void setElement(Class<?> type) {
        this.element = new Object();
        this.type = type;
    }

    public void setValidator(ValidChecker checker) {
        this.checker = checker;
    }

    public Object getElement() {
        return element;
    }

    public String getParameter() {
        return parameter;
    }
}

