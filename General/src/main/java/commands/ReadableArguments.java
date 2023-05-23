package commands;

import logic.IODevice;

public abstract class ReadableArguments<T> {
    T arguments;

    public abstract void read(IODevice io);

    public T getArguments() {
        return arguments;
    }

    public String getInfo() {
        return ""; //TODO: Доопределить выводимую информацию
    }
}
