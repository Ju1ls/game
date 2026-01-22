package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;

import java.util.Map;

public class EnterPlaceCommand extends Command {
    @Getter
    private final String name = "enter";


    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        Player player = game.getPlayer();
        Side currentSide = player.getCurrentSide();
        Location currentLoc = player.getCurrentLocation();
        Location nextLocation = currentSide.getNeighbor();
        String currentDirection = null;

        if (nextLocation == null) {
            System.out.println("There is no way to go in this direction.");
            return PostCommandActionType.NONE;
        }

        if (nextLocation.isLocked()) {
            System.out.println("The " + nextLocation.getName() + " is currently locked.");
            return PostCommandActionType.NONE;
        }

        for (Map.Entry<String, Side> entry : currentLoc.getSides().entrySet()) {
            if (entry.getValue() == currentSide) {
                currentDirection = entry.getKey();
                break;
            }
        }

        if (currentDirection == null) {
            System.out.println("Error: Could not determine current direction.");
            return PostCommandActionType.NONE;
        }

        String arrivalDirection = getOppositeDirection(currentDirection);
        Side arrivalSide = nextLocation.getSides().get(arrivalDirection);

        if (arrivalSide == null) {
            System.out.println("Error: The path seems blocked on the other side.");
            return PostCommandActionType.NONE;
        }

        player.setCurrentLocation(nextLocation);
        player.setCurrentSide(arrivalSide);

        System.out.println("You have entered: " + nextLocation.getName());
        System.out.println("You are now at the " + arrivalDirection + " side.");

        return PostCommandActionType.NONE;
    }

    private String getOppositeDirection(String direction) {
        return switch (direction.toLowerCase()) {
            case "north" -> "south";
            case "south" -> "north";
            case "east" -> "west";
            case "west" -> "east";
            default -> throw new IllegalStateException("Unexpected value: " + direction.toLowerCase());
        };
    }
}
