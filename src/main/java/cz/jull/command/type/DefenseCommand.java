package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.Response;
import lombok.Getter;

/**
 * Command implementation that allows the player to take a defensive stance during combat.
 * @author Julie Šefl
 */
public class DefenseCommand extends Command {
    @Getter
    private final String name = "defense";

    /**
     * Executes the defense maneuver.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        return game.getFightManager().performDefense(game);
    }
}
