package cz.jull;

import cz.jull.command.CommandManager;
import cz.jull.json_loader.GameData;
import cz.jull.mechanics.DetectorBatteryManager;
import cz.jull.mechanics.FightManager;
import cz.jull.mechanics.MentalHealthManager;
import cz.jull.mechanics.ScheduledTaskManager;
import cz.jull.mechanics.dialog.DialogManager;
import cz.jull.models.locations.Direction;
import lombok.Getter;

import java.io.IOException;
import java.util.Scanner;

/**
 * The core engine and state manager for the game.
 * This class initializes all necessary sub-systems (managers), loads game data,
 * handles the main game loop, and processes user input.
 *
 * @author Julie Šefl
 */
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

    private static final String INITIAL_DIALOG = """
                You wake up in an abandoned store, feeling extremely confused and with no memory of what happened or how you got there.\s
                As Aris, you must now explore the ruined city to find your way to safety.\s
                \nType your first command (type 'help' for more commands):\s""";

    /**
     * Initializes and starts the main game loop.
     * This method loads the game data, starts all background scheduled tasks, prints the
     * introductory dialog, and then continuously prompts the user for input until an exit
     * command is received.
     * @throws IOException If there is an error reading the game data files during the load phase.
     * @throws InterruptedException If the thread is interrupted while waiting or sleeping.
     */
    public void startGame() throws IOException, InterruptedException {
        loadGame();
        scheduledTaskManager.startAll();
        System.out.println(INITIAL_DIALOG);

        while (running) {
            System.out.print(">>");
            if (commandManager.runCommand(scanner.nextLine(), this)) {
                running = false;
            }
        }
        scheduledTaskManager.shutdown();
    }

    /**
     * Prepares the game state before the loop begins.
     * Loads location and item data from resources, registers background mechanic managers
     * (like mental health and battery drainage), and sets the player's initial starting location and direction.
     * @throws IOException If the underlying {@link GameData#loadGameDataFromResources()} method fails.
     */
    private void loadGame() throws IOException {
        gameData = GameData.loadGameDataFromResources();
        scheduledTaskManager.register(new MentalHealthManager(this));
        scheduledTaskManager.register(new DetectorBatteryManager(this));
        player.setCurrentLocation(gameData.getLocations().getFirst());
        player.setCurrentSide(player.getCurrentLocation().getSides().get(Direction.NORTH));
    }

}
