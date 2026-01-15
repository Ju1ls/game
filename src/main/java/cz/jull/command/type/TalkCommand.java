package cz.jull.command.type;

import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

public class TalkCommand extends Command {
    @Getter
    private final String name = "talk";


    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;
    }
}
