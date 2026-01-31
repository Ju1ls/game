package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.Item;
import cz.jull.models.locations.Direction;
import cz.jull.models.npc.NPC;
import lombok.Getter;

import java.util.List;

public class ThrowItemCommand extends Command {
    @Getter
    private final String name = "throw";

    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        List<Item> inventory = game.getPlayer().getInventory();
        String itemNameArg = args[0].toLowerCase();

        for (Item item : inventory) {
            String itemName = item.getName().toLowerCase();
            if (!itemName.equals(itemNameArg)) {
                continue;
            }
            game.getPlayer().removeItemFromInventory(item);
        }

        List<NPC> north = game.getPlayer().getCurrentLocation().getSides().get(Direction.NORTH).getNpcs();
        List<NPC> south = game.getPlayer().getCurrentLocation().getSides().get(Direction.SOUTH).getNpcs();
        List<NPC> east = game.getPlayer().getCurrentLocation().getSides().get(Direction.EAST).getNpcs();
        List<NPC> west = game.getPlayer().getCurrentLocation().getSides().get(Direction.WEST).getNpcs();

        Direction side = Direction.fromString(args[1]);

        switch (side) {
            case NORTH -> {
                north.addAll(south);
                north.addAll(east);
                north.addAll(west);
                south.clear();
                east.clear();
                west.clear();
            }
            case SOUTH -> {
                south.addAll(north);
                south.addAll(east);
                south.addAll(west);
                north.clear();
                east.clear();
                west.clear();
            }
            case EAST -> {
                east.addAll(north);
                east.addAll(south);
                east.addAll(west);
                north.clear();
                south.clear();
                west.clear();
            }
            case WEST -> {
                west.addAll(north);
                west.addAll(south);
                west.addAll(east);
                north.clear();
                south.clear();
                east.clear();
            }
        }
        return PostCommandActionType.NONE;
    }
}
