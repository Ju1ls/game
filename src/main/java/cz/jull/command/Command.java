package cz.jull.command;

import cz.jull.Game;

/**
 * The abstract base class for all executable commands in the game.
 */
public abstract class Command {
    public abstract String getName();

    /**
     * Executes the specific logic of the command.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return PostCommandActionType.NONE.
     */
    public abstract Response execute(String[] args, Game game);
}
