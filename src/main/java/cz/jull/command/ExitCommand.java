package cz.jull.command;

import lombok.Getter;

public class ExitCommand extends Command{
    @Getter
    private final String name = "exit";

    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.EXIT;
    }
}
