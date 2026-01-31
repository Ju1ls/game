package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;

public class UseItemCommand extends Command {
    @Getter
    private final String name = "use";


    @Override
    public PostCommandActionType execute(String[] args, Game game) {
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
