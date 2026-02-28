package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.Response;
import lombok.Getter;

/**
 * Command implementation that gives the player a clue about what he should do.
 *
 * @author Julie Šefl
 */
public class ClueCommand extends Command {
    @Getter
    private final String name = "clue";

    /**
     * Executes the clue command.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        return new Response("Find all 4 keys, make it to hidden Bunker and dont die :)");
    }
}
