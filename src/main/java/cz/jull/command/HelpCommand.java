package cz.jull.command;

import lombok.Getter;

public class HelpCommand extends Command{
    @Getter
    private final String name = "help";

    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;

    }
}
