package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import cz.jull.models.locations.Direction;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import cz.jull.models.npc.NPC;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

/**
 * Represents a command that allows the player to throw an item in a specific direction.
 */
public class ThrowItemCommand extends Command {
    @Getter
    private final String name = "throw";

    /**
     * Executes the throw logic, removing the item and aggregating NPCs to the target side.
     * @param args Arguments passed by the user.
     *      * <ul>
     *      * <li>{@code args[0]}: The name of the item to throw.</li>
     *      * <li>{@code args[1]}: The direction to throw (north, south, east, west).</li>
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        Location currentLocation = game.getPlayer().getCurrentLocation();
        EnumMap<Direction, Side> sides = currentLocation.getSides();

        String itemNameArg = args[0].toLowerCase();
        Direction targetDirection = Direction.fromString(args[1]);

        List<Item> tempLocation = new ArrayList<>();
        game.getPlayer().getInventory().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemNameArg))
                .findFirst()
                .ifPresent(item -> {
                    game.getPlayer().removeItemFromInventory(item);
                    tempLocation.add(item);
                });

        List<NPC> targetNpcs = sides.get(targetDirection).getNpcs();
        List<Item> targetItems = sides.get(targetDirection).getItems();

        for (Direction direction : Direction.values()) {
            if (direction != targetDirection && sides.containsKey(direction)) {
                Side side = sides.get(direction);
                targetNpcs.addAll(side.getNpcs());
                side.getNpcs().clear();
            }
        }

        targetItems.addAll(tempLocation);

        return PostCommandActionType.NONE;
    }
}
