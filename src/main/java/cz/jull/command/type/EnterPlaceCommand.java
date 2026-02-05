package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.Command;
import cz.jull.command.Response;
import cz.jull.models.locations.Direction;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;

import java.util.Map;

/**
 * Represents a command that attempts to move the player from the current location to a neighboring one.
 */
public class EnterPlaceCommand extends Command {
    @Getter
    private final String name = "enter";

    /**
     * Method that executes the movement logic to transfer the player to the neighboring location.
     * @param args Arguments passed by the user (not used in this case).
     * @param game The main game instance.
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        Player player = game.getPlayer();
        Side currentSide = player.getCurrentSide();
        Location currentLocation = player.getCurrentLocation();
        Location nextLocation = currentSide.getNeighbor();
        Direction currentDirection = null;

        if (nextLocation == null) {
            return new Response("cant go here, no neighbor");
        }

        if (nextLocation.isLocked()) {
            return new Response("location is locked");
        }

        for (Map.Entry<Direction, Side> entry : currentLocation.getSides().entrySet()) {
            if (entry.getValue() == currentSide) {
                currentDirection = entry.getKey();
                break;
            }
        }

        if (currentDirection == null) {
            return new Response();
        }

        Direction arrivalDirection = currentDirection.getOpposite();
        Side arrivalSide = nextLocation.getSides().get(arrivalDirection);

        if (arrivalSide == null) { // shouldn't happen, but I'd rather put it here
            return new Response();
        }

        player.setCurrentLocation(nextLocation);
        player.setCurrentSide(arrivalSide);

        return new Response("u entered: " + nextLocation);
    }
}
