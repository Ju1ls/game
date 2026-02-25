package cz.jull.command;

import cz.jull.Game;
import cz.jull.command.type.*;

import java.util.Set;

/**
 * Manages the registration and execution of all game commands.
 */
public class CommandManager {
    private final Set<Command> commands = Set.of(
            new AnswerCommand(),
            new AttackCommand(),
            new ClueCommand(),
            new DefenseCommand(),
            new EnterPlaceCommand(),
            new ExitCommand(),
            new GoCommand(),
            new HelpCommand(),
            new HoldBreathCommand(),
            new InventoryCommand(),
            new SearchCommand(),
            new StopDialogCommand(),
            new TakeItemCommand(),
            new TalkCommand(),
            new ThrowItemCommand(),
            new UseItemCommand()
    );

    /**
     * Parses the user's input string, locates the corresponding command, and executes it.
     * @param fullString The raw input string typed by the player.
     * @param game The main game instance.
     * @return true or false based on if the game is over or not.
     */
    public boolean runCommand(String fullString, Game game) {
        if (fullString == null || fullString.trim().isEmpty()) {
            return false;
        }

        for (Command command : commands) {
            if (!fullString.startsWith(command.getName())) {
                continue;
            }

            String[] parts = fullString.replaceFirst("(?i)" + command.getName(), "").trim().split("\\s+");

            if (parts.length == 1 && parts[0].isEmpty()) {
                parts = new String[0];
            }
            Response response = command.execute(parts, game);
            PostCommandActionType type = response.type();

            if (response.value() != null) {
                System.out.println(response.value());
            }
            switch (type) {
                case NONE -> {
                    return false;
                }
                case DEAD -> {
                    System.out.println("You died.");
                    return true;
                }
                case EXIT -> {
                    System.out.println("Exiting...");
                    return true;
                }
            }
        }
        System.out.println("Invalid command. Please try again.");
        return false;
    }
}
