package cz.jull.mechanics;

import cz.jull.Game;
import cz.jull.Player;

import java.util.concurrent.TimeUnit;

/**
 * Represents the mechanic responsible for managing oxygen consumption and suffocation.
 */
public class OxygenManager extends ScheduledTaskManager.Task {
    public OxygenManager(Game game) {
        super(1, TimeUnit.SECONDS, new OxygenDecreaser(game));
    }

    /**
     * The internal runnable logic that executes on the scheduled thread.
     * @param game The main game instance.
     */
    private record OxygenDecreaser(Game game) implements Runnable {
        /**
         * Executes the oxygen/health depletion cycle.
         */
        @Override
        public void run() {
            Player player = game.getPlayer();
            try {
                player.setOxygen(player.getOxygen() - 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (player.getOxygen() <= 0) {
                try {
                    player.setHealth(player.getHealth() - 1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (player.getHealth() <= 0) {
                System.out.println("u r dead");
                System.exit(0);
            }
        }
    }
}
