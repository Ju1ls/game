package cz.jull.command.type;

import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

public class HelpCommand extends Command {
    @Getter
    private final String name = "help";

    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;

    }
}
