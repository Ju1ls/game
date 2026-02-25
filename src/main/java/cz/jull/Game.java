package cz.jull;

import cz.jull.command.CommandManager;
import cz.jull.json_loader.GameData;
import cz.jull.mechanics.DetectorBatteryManager;
import cz.jull.mechanics.FightManager;
import cz.jull.mechanics.MentalHealthManager;
import cz.jull.mechanics.ScheduledTaskManager;
import cz.jull.mechanics.dialog.DialogManager;
import cz.jull.models.Item;
import cz.jull.models.locations.Direction;
import lombok.Getter;

import java.io.IOException;
import java.util.List;
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

    private boolean running = true;

    public void startGame() throws IOException, InterruptedException {
        loadGame();
        scheduledTaskManager.startAll();
        player.setCurrentLocation(gameData.getLocations().getFirst());
        player.setCurrentSide(player.getCurrentLocation().getSides().get(Direction.NORTH));
        commandManager.initialization();
        System.out.println(initialDialog());

        while (running) {
            System.out.print(">>");
            if (commandManager.runCommand(scanner.nextLine(), this)) {
                running = false;
            }
        }
        scheduledTaskManager.shutdown();
    }

    private void loadGame() throws IOException {
        gameData = GameData.loadGameDataFromResources();
        scheduledTaskManager.register(new MentalHealthManager(this));
        scheduledTaskManager.register(new DetectorBatteryManager(this));
    }

    private String initialDialog() {
        return """
                You wake up in an abandoned store, feeling extremely confused and with no memory of what happened or how you got there.\s
                As Aris, you must now explore the ruined city to find your way to safety.\s
                \nType your first command (type 'help' for more commands):\s""";

    }
}
