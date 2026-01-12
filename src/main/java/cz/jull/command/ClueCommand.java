package cz.jull.command;

import lombok.Getter;

public class ClueCommand extends Command{
    @Getter
    private final String name = "clue";

    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;
    }
}
