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

public class EnterPlaceCommand extends Command {
    @Getter
    private final String name = "enter";

    /**
     * Method that executes the movement logic to transfer the player to the neighboring location.
     * @param args Arguments passed by the user (not used in this case).
     * @param game The main game instance.
     * @return PostCommandActionType.NONE.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        Player player = game.getPlayer();
        Side currentSide = player.getCurrentSide();
        Location currentLocation = player.getCurrentLocation();
        Location nextLocation = currentSide.getNeighbor();
        Direction currentDirection = null;

        if (nextLocation == null) { // no neighbor
            return PostCommandActionType.NONE;
        }

        if (nextLocation.isLocked()) { // locked location
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

        return PostCommandActionType.NONE;
    }
}
