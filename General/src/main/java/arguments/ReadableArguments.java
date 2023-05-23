package arguments;

import logic.IODevice;

public abstract class ReadableArguments<T> {
    public ReadableArguments() {
    }

    protected T arguments;

    public abstract void read(IODevice io);

    public T getArguments() {
        return arguments;
    }

    public String getInfo() {
        return ""; //TODO: Доопределить выводимую информацию
    }
}
