package commands;


@FunctionalInterface
public interface ValidChecker {
    void check(String[] param);
}
