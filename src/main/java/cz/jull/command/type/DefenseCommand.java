package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

/**
 * Command implementation that allows the player to take a defensive stance during combat.
 */
public class DefenseCommand extends Command {
    @Getter
    private final String name = "defense";

    /**
     * Executes the defense maneuver.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#DEAD} if the reduced damage is still enough
     * to kill the player; otherwise {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args,  Game game) {
        return game.getFightManager().performDefense(game);
    }
}
