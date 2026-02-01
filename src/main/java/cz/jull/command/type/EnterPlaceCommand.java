package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
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
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        Player player = game.getPlayer();
        Side currentSide = player.getCurrentSide();
        Location currentLocation = player.getCurrentLocation();
        Location nextLocation = currentSide.getNeighbor();
        Direction currentDirection = null;

        if (nextLocation == null) {
            System.out.println("cant go here, no neighbor");// no neighbor
            return PostCommandActionType.NONE;
        }

        if (nextLocation.isLocked()) {
            System.out.println("location is locked");// locked location
            return PostCommandActionType.NONE;
        }

        for (Map.Entry<Direction, Side> entry : currentLocation.getSides().entrySet()) {
            if (entry.getValue() == currentSide) {
                currentDirection = entry.getKey();
                break;
            }
        }

        if (currentDirection == null) {
            return PostCommandActionType.NONE;
        }

        Direction arrivalDirection = currentDirection.getOpposite();
        Side arrivalSide = nextLocation.getSides().get(arrivalDirection);

        if (arrivalSide == null) { // shouldn't happen, but I'd rather put it here
            return PostCommandActionType.NONE;
        }

        player.setCurrentLocation(nextLocation);
        player.setCurrentSide(arrivalSide);
        System.out.println("u entered: " + nextLocation);

        return PostCommandActionType.NONE;
    }
}
