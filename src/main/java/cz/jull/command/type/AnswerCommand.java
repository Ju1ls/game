package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

public class AnswerCommand extends Command {
    @Getter
    private final String name = "answer";


    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        return PostCommandActionType.NONE;
    }
}
