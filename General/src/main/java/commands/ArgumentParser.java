package commands;

import constants.Constants;
import constants.Messages;
import exceptions.BadParametersException;
import logic.IODevice;

public class ArgumentParser {
    private String[] parameters;
    private ValidChecker checker;
    private Object[] elements;
    private Class<?> type;

    ArgumentParser() {
    }

    public ArgumentParser parseFrom(IODevice io) {
        String[] param = io.readLine().split(Constants.SPLITTER, 1)[1].split(Constants.SPLITTER);
        if (parameters != null) {
            if (param.length == 0) throw new BadParametersException(Messages.getMessage("warning.empty_argument"));
            if (checker != null) checker.check(param);
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


    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public void setElements(Class<?> type, int number) {
        this.elements = new Object[number];
        this.type = type;
    }

    public void setValidator(ValidChecker checker) {
        this.checker = checker;
    }

    public Object[] getElements() {
        return elements;
    }

    public String[] getParameters() {
        return parameters;
    }

    public Class<?> getType() {
        return type;
    }
}

