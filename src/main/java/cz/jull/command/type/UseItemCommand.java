package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.Response;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;
import java.util.Set;

/**
 * Represents a command that allows the player to utilize an item from their inventory.
 *
 * @author Julie Šefl
 */
public class UseItemCommand extends Command {
    @Getter
    private final String name = "use";


    /**
     * Executes the logic to find and use a specific item.
     * @param args Arguments passed by the user, where {@code args[0]} is expected to be the item name.
     * @param game The main game instance.
     * @return {@link Response}
     * @throws RuntimeException if the specific {@link Item#useItem(Game)} logic throws an exception.
     */
    @Override
    public Response execute(String[] args, Game game) {
        if (args == null || args.length == 0) {
            return new Response("You must specify which item to use.");
        }

        Set<Item> inventory = game.getPlayer().getInventory();
        String itemNameArg = String.join(" ", args).toLowerCase();
        for (Item item : inventory) {
            String itemName = item.getName().toLowerCase();
            if (!itemName.equals(itemNameArg)) {
                continue;
            }
            try {
                item.useItem(game);
                if (item.isSingleUse()) {
                    game.getPlayer().removeItemFromInventory(item);
                }
                return new Response();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return new Response("No item found.");
    }
}
