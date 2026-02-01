package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

/**
 * Represents the "help" command within the game.
 */
public class HelpCommand extends Command {
    @Getter
    private final String name = "help";

    /**
     * Executes the help logic.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        Player player = game.getPlayer();
        System.out.println("""
                All commands: \s
                go (moves player to one side of a location, eg. "go north"),\s
                enter (moves player to different location, eg. "enter"),\s
                use (allows player to use an item, eg. "use knife"),\s
                throw (player throws an item from their inventory, eg. "throw knife north"),\s
                talk (used to talk with non-hostile NPCs, eg. "talk Arthur"),\s
                take (allows player to take an item, eg. "take knife"),\s
                stop dialog (used to stop talking to an non-hostile NPC, eg. "stop dialog"),\s
                search (used to search a location for hidden items, "eg. "search"),\s
                inventory (opens inventory, eg. "inventory"),\s
                hold breath (toggles holding breath and breathing, eg. "hold breath"),\s
                help (shows all available commands and player stats, eg. "help"),\s
                exit (exits the game, eg. "exit"),\s
                defense (used in combat for defense, eg. "defense"),\s
                clue (gives a clue, eg. "clue"),\s
                attack (used in combat to attack hostile NPCs, eg. "attack"),\s
                answer (used to answer to non-hostile NPCs, eg. "answer 1"); \s
                """);
        System.out.println("Stats: \n" +
                "Health: " + player.getHealth() + "/100\n" +
                "Mental health: " + player.getHealth() + "/100\n" +
                "Oxygen level: " + player.getOxygen() + "/100\n" +
                "Current location: " + player.getCurrentLocation().getName() + "\n"
        );
        return PostCommandActionType.NONE;
    }
}
