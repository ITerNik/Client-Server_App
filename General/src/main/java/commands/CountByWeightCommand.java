package commands;

import constants.Messages;
import logic.Manager;

public class CountByWeightCommand extends AbstractCommand {
    private int count;
    public CountByWeightCommand() {
        setParameterName("weight");
        setValidator(param -> {
            try {
                Double.parseDouble(param);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(Messages.getMessage("warning.format.not_real",
                        Messages.getMessage("parameter.weight")));
            }
        });
    }

    public CountByWeightCommand(Manager manager) {
        this();
        this.manager = manager;
    }

    @Override
    public void execute() {
        count = manager.countByWeight(Double.parseDouble(parser.getParameter()));
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
