package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

/**
 * Command implementation used to terminate the game session.
 */
public class ExitCommand extends Command {
    @Getter
    private final String name = "exit";

    /**
     * Triggers the game termination sequence.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        return PostCommandActionType.EXIT;
    }
}
