package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.mechanics.OxygenManager;
import lombok.Getter;

public class HoldBreathCommand extends Command {
    @Getter
    private final String name = "hold breath";

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
