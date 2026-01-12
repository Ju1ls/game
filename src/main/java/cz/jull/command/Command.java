package cz.jull.command;

public abstract class Command {
    public abstract String getName();
    public abstract PostCommandActionType execute(String[] args);
}
