package cz.jull.mechanics;

import cz.jull.Game;
import cz.jull.Player;

import java.util.concurrent.TimeUnit;

/**
 * Represents the mechanic responsible for the passive energy drain of the player's detector device.
 *
 * @author Julie Šefl
 */
public class DetectorBatteryManager extends ScheduledTaskManager.Task{
    public DetectorBatteryManager(Game game) {
        super(1, TimeUnit.MINUTES, new DetectorBatteryDecreaser(game));
    }

    /**
     * The internal runnable logic that executes on the scheduled thread.
     * @param game The main game instance.
     */
    private record DetectorBatteryDecreaser(Game game) implements Runnable {
        /**
         * Executes the battery depletion cycle.
         */
        @Override
        public void run() {
            Player player = game.getPlayer();
            int current = player.getDetectorBatteryLevel();
            try {
                if (current > 0) {
                    player.setDetectorBatteryLevel(current - 1);
                }
                if (player.getDetectorBatteryLevel() == 10) {
                    System.out.println("You have low battery.");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (player.getDetectorBatteryLevel() <= 0) {
                System.out.println("You can't use detector.");
            }
        }
    }
}
