package arguments;

import commands.ReadableArguments;
import constants.Messages;
import exceptions.BadParametersException;
import logic.IODevice;

public class NoReadableArguments extends ReadableArguments<String> {
    @Override
    public void read(IODevice io) {
        if (io.readLine().isBlank())
            throw new BadParametersException(
                    Messages.getMessage("warning.needless_argument"));
    }
}
