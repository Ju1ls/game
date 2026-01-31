package cz.jull;

import cz.jull.json_loader.GameData;
import cz.jull.mechanics.DetectorBatteryManager;
import cz.jull.mechanics.MentalHealthManager;
import cz.jull.mechanics.ScheduledTaskManager;
import lombok.Getter;

import java.io.IOException;

public class Game {
    @Getter
    private Player player;

    @Getter
    private GameData gameData;

    @Getter
    private final ScheduledTaskManager scheduledTaskManager = new ScheduledTaskManager();

    public void startGame() throws IOException {
        loadGame();
        scheduledTaskManager.startAll();
    }

    private void loadGame() throws IOException {
        gameData = GameData.loadGameDataFromResources();
        scheduledTaskManager.register(new MentalHealthManager(this));
        scheduledTaskManager.register(new DetectorBatteryManager(this));
    }
}
