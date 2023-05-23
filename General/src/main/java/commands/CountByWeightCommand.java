package commands;

import constants.Messages;
import exceptions.BadParametersException;
import logic.IODevice;
import logic.Manager;

public class CountByWeightCommand extends AbstractCommand {
    private int count;

    public CountByWeightCommand() {
    }

    public CountByWeightCommand(Manager manager) {
        super(manager);
    }
    {
        readable = new ReadableArguments<Double>() {
            @Override
            public void read(IODevice io) {
                try {
                    arguments = Double.parseDouble(io.read());
                } catch (NumberFormatException e) {
                    throw new BadParametersException(Messages.getMessage("warning.format.not_real",
                            Messages.getMessage("parameter.weight")));
                    //TODO: BadParam -> Exception
                }
            }
        };
    }

    @Override
    public boolean execute() {
        count = manager.countByWeight((double) readable.getArguments());
        return true;
    }

    @Override
    public String getName() {
        return "count_by_weight";
    }

    @Override
    public String getReport() {
        return Messages.getMessage("message.format.count", count);
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.count_by_weight");
    }
}
