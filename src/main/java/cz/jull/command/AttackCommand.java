package cz.jull.command;

import lombok.Getter;

public class AttackCommand extends Command{
    @Getter
    private final String name = "attack";

    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;
    }
}
