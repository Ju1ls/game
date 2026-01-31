package cz.jull.mechanics;

import cz.jull.Game;
import cz.jull.Player;

import java.util.concurrent.TimeUnit;

public class MentalHealthManager extends ScheduledTaskManager.Task{
    public MentalHealthManager(Game game) {
        super(5, TimeUnit.SECONDS, new MentalHealthDecreaser(game));
    }

    private record MentalHealthDecreaser(Game game) implements Runnable {

        @Override
        public void run() {
            Player player = game.getPlayer();
            try {
                player.setMentalHealth(player.getMentalHealth() - 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (player.getMentalHealth() <= 0) {
                System.out.println("u got insane");
                System.exit(0);
            }
        }
    }
}
