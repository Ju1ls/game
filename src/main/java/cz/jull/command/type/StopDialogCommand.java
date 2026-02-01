package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

/**
 * Command implementation that allows the player to end an active conversation.
 */
public class StopDialogCommand extends Command {
    @Getter
    private final String name = "stop dialog";

    /**
     * Terminates the current dialogue session immediately.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        game.getDialogManager().stopDialog();
        return PostCommandActionType.NONE;
    }
}
