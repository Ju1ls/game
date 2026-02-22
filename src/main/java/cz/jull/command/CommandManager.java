package cz.jull.command;

import cz.jull.Game;
import cz.jull.command.type.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Manages the registration and execution of all game commands.
 */
public class CommandManager {
    private final Set<Command> commands = new HashSet<>();

    /**
     * Parses the user's input string, locates the corresponding command, and executes it.
     * @param fullString The raw input string typed by the player.
     * @param game The main game instance.
     * @return true or false based on if the game is over or not.
     */
    public boolean runCommand(String fullString, Game game) {
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
        return false;
    }

    /**
     * Registers all available commands into the internal set.
     */
    public void initialization() {
        commands.add(new AnswerCommand());
        commands.add(new AttackCommand());
        commands.add(new ClueCommand());
        commands.add(new DefenseCommand());
        commands.add(new EnterPlaceCommand());
        commands.add(new ExitCommand());
        commands.add(new GoCommand());
        commands.add(new HelpCommand());
        commands.add(new HoldBreathCommand());
        commands.add(new InventoryCommand());
        commands.add(new SearchCommand());
        commands.add(new StopDialogCommand());
        commands.add(new TakeItemCommand());
        commands.add(new TalkCommand());
        commands.add(new ThrowItemCommand());
        commands.add(new UseItemCommand());
    }
}
