package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SearchCommand extends Command {
    @Getter
    private final String name = "search";

    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        List<Item> itemsInLocation = game.getPlayer().getCurrentSide().getItems();
        List<Item> unhiddenItems = new ArrayList<>();

        for (Item item : itemsInLocation) {
            if (item.isHidden()) {
                unhiddenItems.add(item);
                item.setHidden(false);
            }
        }
        System.out.println("Items you found: " + unhiddenItems);

        return PostCommandActionType.NONE;
    }
}
