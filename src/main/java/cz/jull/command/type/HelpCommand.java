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
            System.out.println(text);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Response("Stats: \n" +
                "Health: " + player.getHealth() + "/100\n" +
                "Mental health: " + player.getMentalHealth() + "/100\n" +
                "Oxygen level: " + player.getOxygen() + "/100\n" +
                "Battery level: " + player.getDetectorBatteryLevel() + "/100\n" +
                "Current location: " + player.getCurrentLocation().getName() + "\n");
    }
}
