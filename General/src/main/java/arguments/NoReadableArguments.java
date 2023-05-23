package arguments;

import constants.Messages;
import exceptions.BadParametersException;
import logic.IODevice;

public class NoReadableArguments extends ReadableArguments<String> {
    public NoReadableArguments() {
    }

    @Override
    public void read(IODevice io) {
        if (io.hasNext() && !io.readLine().isBlank())
            throw new BadParametersException(
                    Messages.getMessage("warning.needless_argument"));
    }
}
