package arguments;

import constants.Messages;
import exceptions.BadParametersException;
import logic.IODevice;
public class WeightArguments implements Readable<Double> {
    public WeightArguments() {
    }

    @Override
    public Double read(IODevice io) {
        double input = 0;
        try {
            input = Double.parseDouble(io.read());
        } catch (NumberFormatException e) {
            throw new BadParametersException(Messages.getMessage("warning.format.not_real",
                    Messages.getMessage("parameter.weight")));
            //TODO: BadParam -> Exception
        }
        return input;
    }
}
