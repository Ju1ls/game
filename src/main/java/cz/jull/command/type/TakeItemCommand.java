package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.Command;
import cz.jull.command.Response;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;

/**
 * Represents a command that allows the player to pick up an item from their current location.
 *
 * @author Julie Šefl
 */
public class TakeItemCommand extends Command {
    @Getter
    private final String name = "take";

    /**
     * Executes the logic to transfer an item from the location to the player.
     * @param args Arguments passed by the user. {@code args[0]} represents the target item name.
     * @param game The main game instance.
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        Player player = game.getPlayer();

        if (player.getCurrentSide() == null) {
            return new Response("You aren't looking at anything specific where items could be.");
        }

        List<Item> itemsInLocation = player.getCurrentSide().getItems();

        if (args.length == 0) {
            return new Response("What do u want to take.");
        }

        String itemNameArg = args[0].toLowerCase();
        Item itemToTake = null;

        for (Item item : itemsInLocation) {
            if (item.isHidden()) {
                continue;
            }

            if (item.getName().toLowerCase().startsWith(itemNameArg)) {
                itemToTake = item;
                break;
            }
        }

        if (itemToTake == null) {
            return new Response("Item not found.");
        }
        if (player.getInventory().size() >= 6) {
            return new Response("You can't have more than 6 things in your inventory.");
        }

        player.addItemToInventory(itemToTake);
        itemsInLocation.remove(itemToTake);

        return new Response("You took: " + itemToTake.getName());
    }
}
