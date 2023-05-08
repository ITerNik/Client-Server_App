package commands;

import exceptions.BadParametersException;
import logic.ConsoleService;
import logic.FileDevice;
import logic.IODevice;
import logic.Manager;
import constants.Messages;

import java.io.FileNotFoundException;

public class ExecuteScriptCommand extends AbstractCommand {

    private final ConsoleService.CommandBuilder builder;
    private FileDevice fio;

    private String report = "";

    @Override
    protected void checkArguments(String[] param) throws BadParametersException {
        if (!builder.addToFileHistory(param[0])) throw new BadParametersException(Messages.getMessage("warning.cycle"));
        try {
            fio = new FileDevice(param[0]);
        } catch (FileNotFoundException e) {
            throw new BadParametersException(Messages.getMessage("warning.format.file_not_found", param[0]));
        }
    }

    public ExecuteScriptCommand(IODevice cio, Manager manager, ConsoleService.CommandBuilder builder) {
        super(cio, manager);
        this.builder = builder;
        setParameterNames("file_name");
    }

    @Override
    public void execute() {
        builder.setDevice(fio);
        while (fio.hasNextLine()) {
            String commandLine = fio.readLine();
            Command current = builder.build(commandLine);
            current.execute();
            report += String.format("Результат команды %s:%n", current.getName());
            report += current.getReport() + "\n";
        }
    }

    @Override
    public String getName() {
        return "execute_script";
    }

    @Override
    public String getReport() {
        return report;
    }

    @Override
    public String getInfo() {
        return Messages.getMessage("command.execute_script");
    }
}
