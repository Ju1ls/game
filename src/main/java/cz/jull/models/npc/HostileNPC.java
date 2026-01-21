package cz.jull.models.npc;

import cz.jull.models.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HostileNPC extends NPC {
    private int health;
    private int strength;

    public HostileNPC(String id, String name, String description, List<Item> items, int health, int strength) {
        super(id, name, description, items);
        this.health = health;
        this.strength = strength;
    }

    @Override
    public void interact() {

    }
}
