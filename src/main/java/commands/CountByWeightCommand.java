package commands;

import logic.IODevice;
import logic.Manager;
import constants.Messages;

public class CountByWeightCommand extends AbstractCommand {
    private double weight;
    private int count;

    public CountByWeightCommand(IODevice io, Manager manager) {
        super(io, manager);
        setParameterNames("weight");
    }

    @Override
    protected void checkArguments(String[] param) throws IllegalArgumentException {
        try {
            weight = Double.parseDouble(param[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Messages.getMessage("warning.format.not_real", Messages.getMessage("parameter.weight")));
        }
    }

    @Override
    public void execute() {
        count = manager.countByWeight(weight);
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
