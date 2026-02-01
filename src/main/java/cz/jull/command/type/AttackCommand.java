package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

/**
 * Command implementation that triggers a physical attack against an enemy.
 */
public class AttackCommand extends Command {
    @Getter
    private final String name = "attack";

    /**
     * Executes the attack sequence.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#DEAD} if the enemy's attack
     * kills the player; otherwise {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        return game.getFightManager().performAttack(game);
    }
}
