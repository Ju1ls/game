package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.command.Response;
import lombok.Getter;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        Player player = game.getPlayer();

        try (InputStream stream = PostCommandActionType.class.getClassLoader().getResourceAsStream("help_command.txt")) {
            assert stream != null;
            String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

            String[] lines = text.split("\\r?\\n");

            for (String line : lines) {
                int bracketIndex = line.indexOf(" (");

                if (bracketIndex != -1) {
                    String commandName = line.substring(0, bracketIndex);
                    String description = line.substring(bracketIndex);

                    String CYAN = "\u001B[38;2;71;252;249m";
                    String RESET = "\u001B[0m";
                    System.out.println(CYAN + commandName + RESET + description);
                } else {
                    System.out.println(line);
                    System.out.println();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response("\nStats: \n" +
                "\u001B[32mHealth\u001B[0m: " + player.getHealth() + "/100\n" +
                "\u001B[35mMental health\u001B[0m: " + player.getMentalHealth() + "/100\n" +
                "\u001B[36mOxygen level\u001B[0m: " + player.getOxygen() + "/100\n" +
                "\u001B[38;5;208mBattery level\u001B[0m: " + player.getDetectorBatteryLevel() + "/100\n" +
                "\u001B[34mCurrent location\u001B[0m: " + player.getCurrentLocation().getName() + "\n");
    }
}
