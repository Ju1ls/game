package cz.jull.command.type;

import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

public class StopDialogCommand extends Command {
    @Getter
    private final String name = "stop dialog";


    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;
    }
}
