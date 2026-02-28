package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;

import cz.jull.command.Response;
import cz.jull.models.locations.Direction;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;

/**
 * Represents a command that changes the player's orientation within the current location.
 *
 * @author Julie Šefl
 */
public class GoCommand extends Command {

    @Getter
    private final String name = "go";

    /**
     * Rotates the player to face a specific direction within the current location.
     * @param args Arguments passed by the user (in this case directions like: "north", "south", "east" and "west").
     * @param game The main game instance.
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        if (args.length > 0) {
            try {
                Direction direction = Direction.fromString(args[0].toLowerCase());
                Location currentLoc = game.getPlayer().getCurrentLocation();

                Side nextSide = currentLoc.getSides().get(direction);

                if (nextSide == null) {
                    return new Response("You can't go that way.");
                }

                game.getPlayer().setCurrentSide(nextSide);

                String resultText = "You headed " + direction.toString().toLowerCase() + ".\n" +
                        "--------------------------------\n" +
                        nextSide;

                return new Response(resultText);

            } catch (IllegalArgumentException e) {
                return new Response("Invalid direction: " + args[0]);
            }
        } else {
            return new Response("Go where?");
        }
    }
}
