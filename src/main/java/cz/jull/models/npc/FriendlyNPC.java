package cz.jull.models.npc;

import cz.jull.models.Item;

import java.util.List;

public class FriendlyNPC extends NPC {
    public FriendlyNPC(String id, String name, String description, List<Item> items) {
        super(id, name, description, items);
    }

    @Override
    public void interact() {

    }
}
