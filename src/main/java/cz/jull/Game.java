package cz.jull;

import cz.jull.command.CommandManager;
import cz.jull.json_loader.GameData;
import cz.jull.mechanics.DetectorBatteryManager;
import cz.jull.mechanics.FightManager;
import cz.jull.mechanics.MentalHealthManager;
import cz.jull.mechanics.ScheduledTaskManager;
import cz.jull.mechanics.dialog.DialogManager;
import lombok.Getter;

import java.io.IOException;
import java.util.Scanner;

public class Game {
    @Getter
    private final Player player = new Player();

    @Getter
    private GameData gameData;

    @Getter
    private final ScheduledTaskManager scheduledTaskManager = new ScheduledTaskManager();

    @Getter
    private final FightManager fightManager = new FightManager();

    @Getter
    private final DialogManager dialogManager = new DialogManager();

    @Getter
    private final CommandManager commandManager = new CommandManager();

    @Getter
    private final Scanner scanner = new Scanner(System.in);

    public void startGame() throws IOException {
        loadGame();
        //scheduledTaskManager.startAll();
        player.setCurrentLocation(gameData.getLocations().getFirst());
        commandManager.initialization();
        while (true) { //for testing
            commandManager.runCommand(scanner.nextLine(), this);
        }

    }

    private void loadGame() throws IOException {
        gameData = GameData.loadGameDataFromResources();
        //scheduledTaskManager.register(new MentalHealthManager(this));
        //scheduledTaskManager.register(new DetectorBatteryManager(this));
    }
}
