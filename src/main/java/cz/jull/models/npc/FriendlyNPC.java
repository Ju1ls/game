package cz.jull.models.npc;

import cz.jull.models.Item;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class FriendlyNPC extends NPC {
    public FriendlyNPC(String id, String name, String description, List<Item> items) {
        super(id, name, description, items);
    }

    @Override
    public void interact() {

    }
}
