package commands;

import arguments.ArgumentReader;
import arguments.FileArguments;
import logic.CommandBuilder;
import logic.Manager;
import constants.Messages;
import sendings.Query;

import java.util.ArrayList;
import java.util.HashMap;


public class ExecuteScriptCommand extends AbstractCommand { //TODO: Fix deserialization error

    private final StringBuilder report = new StringBuilder();

    public ExecuteScriptCommand(Manager manager, HashMap<String, ArgumentReader<?>> commandInfo) {
        super(manager);
        reader = new ArgumentReader<>(new FileArguments(commandInfo));
    }

    @Override
    public void execute() {
        CommandBuilder builder = new CommandBuilder(manager);
        ArrayList<Query> queries = (ArrayList<Query>) reader.getArgument();
        for (Query query : queries) {
            Command current = builder.build(query);
            current.execute();
            // builder.logCommand(current);
            report.append(String.format("Результат команды %s:%n", current.getName()))
                    .append(current.getReport());
        }
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getReport() {
        return report.toString();
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.execute_script");
    }
}