package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;

/**
 * Represents a command that allows the player to utilize an item from their inventory.
 */
public class UseItemCommand extends Command {
    @Getter
    private final String name = "use";


    /**
     * Executes the logic to find and use a specific item.
     * @param args Arguments passed by the user, where {@code args[0]} is expected to be the item name.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     * @throws RuntimeException if the specific {@link Item#useItem(Game)} logic throws an exception.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        if (args == null || args.length == 0) {
            System.out.println("U must specify which item to use");
            return PostCommandActionType.NONE;
        }

        List<Item> inventory = game.getPlayer().getInventory();
        String itemNameArg = args[0].toLowerCase();
        for (Item item : inventory) {
            String itemName = item.getName().toLowerCase();
            if (!itemName.equals(itemNameArg)) {
                continue;
            }
            try {
                item.useItem(game);
                break;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return PostCommandActionType.NONE;
    }
}
