package cz.jull.command;

import lombok.Getter;

public class GoCommand extends Command {

    @Getter
    private final String name = "go";

    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;
    }
}
