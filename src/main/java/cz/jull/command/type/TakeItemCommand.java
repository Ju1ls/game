package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;

/**
 * Represents a command that allows the player to pick up an item from their current location.
 */
public class TakeItemCommand extends Command {
    @Getter
    private final String name = "take";

    /**
     * Executes the logic to transfer an item from the location to the player.
     * @param args Arguments passed by the user. {@code args[0]} represents the target item name.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        Player player = game.getPlayer();
        List<Item> itemsInLocation = player.getCurrentSide().getItems();

        if (args.length == 0) {
            System.out.println("what do u want to take");
            return PostCommandActionType.NONE;
        }

        String itemNameArg = args[0].toLowerCase();
        Item itemToTake = null;

        for (Item item : itemsInLocation) {
            if (item.isHidden()) {
                continue;
            }

            if (item.getName().toLowerCase().equals(itemNameArg)) {
                itemToTake = item;
                break;
            }
        }

        if (itemToTake == null) {
            System.out.println("item not found");
            return PostCommandActionType.NONE;
        }
        if (player.getInventory().size() >= 6) {
            System.out.println("cant have more than 6 things");
            return PostCommandActionType.NONE;
        }

        player.addItemToInventory(itemToTake);
        itemsInLocation.remove(itemToTake);

        return PostCommandActionType.NONE;
    }
}
