package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

public class UseItemCommand extends Command {
    @Getter
    private final String name = "use";


    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        return PostCommandActionType.NONE;
    }
}
