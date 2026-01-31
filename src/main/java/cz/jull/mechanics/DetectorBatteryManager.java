package cz.jull.mechanics;

import cz.jull.Game;
import cz.jull.Player;

import java.util.concurrent.TimeUnit;

public class DetectorBatteryManager extends ScheduledTaskManager.Task{
    public DetectorBatteryManager(Game game) {
        super(1, TimeUnit.MINUTES, new DetectorBatteryDecreaser(game));
    }

    private record DetectorBatteryDecreaser(Game game) implements Runnable {

        @Override
        public void run() {
            Player player = game.getPlayer();
            try {
                player.setDetectorBatteryLevel(player.getDetectorBatteryLevel() - 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (player.getDetectorBatteryLevel() <= 0) {
                System.out.println("u cant use detector");
            }
        }
    }
}
