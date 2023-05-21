package logic;

import exceptions.BadConnectionException;

public interface Service {
    void run();
    void setConnection() throws BadConnectionException;
}
