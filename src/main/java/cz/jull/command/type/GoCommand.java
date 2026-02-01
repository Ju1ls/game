package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;

import cz.jull.models.locations.Direction;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;

/**
 * Represents a command that changes the player's orientation within the current location.
 */
public class GoCommand extends Command {

    @Getter
    private final String name = "go";

    /**
     * Rotates the player to face a specific direction within the current location.
     * @param args Arguments passed by the user (in this case directions like: "north", "south", "east" and "west").
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        Direction direction = Direction.fromString(args[0].toLowerCase());
        Location currentLoc = game.getPlayer().getCurrentLocation();

        Side nextSide = currentLoc.getSides().get(direction);
        game.getPlayer().setCurrentSide(nextSide);
        System.out.println("u got to: " + nextSide);
        return PostCommandActionType.NONE;
    }
}
