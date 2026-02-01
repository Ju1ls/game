package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

public class ClueCommand extends Command {
    @Getter
    private final String name = "clue";

    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        System.out.println("Find all 4 keys, make it to hidden Bunker and dont die :)");
        return PostCommandActionType.NONE;
    }
}
