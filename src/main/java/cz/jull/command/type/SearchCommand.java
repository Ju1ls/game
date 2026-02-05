package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.Response;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a command that allows the player to search their current surroundings.
 */
public class SearchCommand extends Command {
    @Getter
    private final String name = "search";

    /**
     * Executes the search logic in the player's current location.
     * @param args Arguments passed by the user (not used in this case).
     * @param game The main game instance.
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        List<Item> itemsInLocation = game.getPlayer().getCurrentSide().getItems();
        List<Item> unhiddenItems = new ArrayList<>();

        for (Item item : itemsInLocation) {
            if (item.isHidden()) {
                unhiddenItems.add(item);
                item.setHidden(false);
            }
        }
        return new Response("Items you found: " + unhiddenItems);
    }
}
