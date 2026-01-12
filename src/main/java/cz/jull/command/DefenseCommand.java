package cz.jull.command;

import lombok.Getter;

public class DefenseCommand extends Command{
    @Getter
    private final String name = "defense";


    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;
    }
}
