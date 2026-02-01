package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import lombok.Getter;

import java.util.List;

public class InventoryCommand extends Command {
    @Getter
    private final String name = "inventory";

    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        List<Item> inventory = game.getPlayer().getInventory();
        System.out.println(inventory); // dont kill me pls
        return PostCommandActionType.NONE;
    }
}
