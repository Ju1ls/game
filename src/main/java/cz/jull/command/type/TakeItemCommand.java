package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;

public class TakeItemCommand extends Command {
    @Getter
    private final String name = "take";


    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        List<Item> itemsInLocation = game.getPlayer().getCurrentSide().getItems();
        String itemNameArg = args[0].toLowerCase();

        for (Item item : itemsInLocation) {
            if (item.isHidden()) {
                continue;
            }
            String itemName = item.getName().toLowerCase();
            if (!itemName.equals(itemNameArg)) {
                continue;
            }
            game.getPlayer().addItemToInventory(item);
            break;
        }

        return PostCommandActionType.NONE;
    }
}
