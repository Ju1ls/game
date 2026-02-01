package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.mechanics.OxygenManager;
import lombok.Getter;

/**
 * Represents a command that toggles the player's breath-holding state.
 */
public class HoldBreathCommand extends Command {
    @Getter
    private final String name = "hold breath";

    /**
     * Executes the breath-holding logic based on the player's current state.
     * @param args Arguments passed by the user (not used in this case).
     * @param game The main game instance.
     * @return PostCommandActionType.NONE.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        if (game.getPlayer().getHoldingBreathTask() == null) {
            game.getPlayer().setHoldingBreathTask(game.getScheduledTaskManager().registerImmediately(new OxygenManager(game)));
        } else {
            game.getPlayer().getHoldingBreathTask().cancel(false);
            game.getPlayer().setHoldingBreathTask(null);
        }
        return PostCommandActionType.NONE;
    }
}
