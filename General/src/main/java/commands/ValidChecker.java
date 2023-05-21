package commands;


import java.io.Serializable;

@FunctionalInterface
public interface ValidChecker extends Serializable {
    void check(String param);
}
