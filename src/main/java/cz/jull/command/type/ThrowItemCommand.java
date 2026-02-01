package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import cz.jull.models.locations.Direction;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import cz.jull.models.npc.HostileNPC;
import cz.jull.models.npc.NPC;
import lombok.Getter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

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
        if (args.length < 2) {
            System.out.println("type what to throw and where");
            return PostCommandActionType.NONE;
        }

        String directionStr = args[args.length - 1];
        Direction targetDirection;
        try {
            targetDirection = Direction.fromString(directionStr);
        } catch (IllegalArgumentException e) {
            System.out.println(directionStr + " is not a valid direction");
            return PostCommandActionType.NONE;
        }

        StringBuilder itemNameBuilder = new StringBuilder();
        for (int i = 0; i < args.length - 1; i++) {
            itemNameBuilder.append(args[i]);
            if (i < args.length - 2) itemNameBuilder.append(" ");
        }
        String itemNameArg = itemNameBuilder.toString().toLowerCase();

        List<Item> itemsToThrow = new ArrayList<>();
        game.getPlayer().getInventory().stream()
                .filter(item -> item.getName().equalsIgnoreCase(itemNameArg))
                .findFirst()
                .ifPresent(item -> {
                    game.getPlayer().removeItemFromInventory(item);
                    itemsToThrow.add(item);
                });

        if (itemsToThrow.isEmpty()) {
            System.out.println("u don't have a " + itemNameArg + " to throw.");
            return PostCommandActionType.NONE;
        }

        Location currentLocation = game.getPlayer().getCurrentLocation();
        EnumMap<Direction, Side> sides = currentLocation.getSides();

        Side targetSide = sides.get(targetDirection);
        List<NPC> targetNpcs = targetSide.getNpcs();
        List<Item> targetItems = targetSide.getItems();

        boolean hostileMoved = false;

        for (Direction direction : Direction.values()) {
            if (direction == targetDirection || !sides.containsKey(direction)) {
                continue;
            }

            Side sourceSide = sides.get(direction);

            List<NPC> hostiles = sourceSide.getNpcs().stream()
                    .filter(npc -> npc instanceof HostileNPC)
                    .collect(Collectors.toList());

            if (!hostiles.isEmpty()) {
                targetNpcs.addAll(hostiles);
                sourceSide.getNpcs().removeAll(hostiles);
                hostileMoved = true;
            }
        }

        targetItems.addAll(itemsToThrow);

        System.out.println("u threw the " + itemNameArg + " " + directionStr);
        if (hostileMoved) {
            System.out.println("monster moved ");
        } else {
            System.out.println("no monster noticed");
        }

        return PostCommandActionType.NONE;
    }
}
