package cz.jull.mechanics;

import cz.jull.Game;
import cz.jull.Player;

import java.util.concurrent.TimeUnit;

/**
 * Represents the mechanic responsible for the passive degradation of the player's mental state.
 *
 * @author Julie Šefl
 */
public class MentalHealthManager extends ScheduledTaskManager.Task{
    public MentalHealthManager(Game game) {
        super(1, TimeUnit.MINUTES, new MentalHealthDecreaser(game));
    }

    /**
     * The internal runnable logic that executes on the scheduled thread.
     * @param game The main game instance.
     */
    private record MentalHealthDecreaser(Game game) implements Runnable {
        /**
         * Executes the sanity reduction cycle.
         */
        @Override
        public void run() {
            Player player = game.getPlayer();
            try {
                if (player.getCurrentLocation().getId().equals("loc_prefab_houses") || player.getCurrentLocation().getId().equals("loc_train_station")) {
                    player.setMentalHealth(player.getMentalHealth() - 2);
                } else {
                    player.setMentalHealth(player.getMentalHealth() - 1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (player.getMentalHealth() <= 0) {
                System.out.println("You lost your sanity.");
                System.exit(0);
            }
        }
    }
}
