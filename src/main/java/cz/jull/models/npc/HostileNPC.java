package cz.jull.models.npc;

import cz.jull.Game;
import cz.jull.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a hostile entity within the game world.
 */
@Getter
@Setter
@NoArgsConstructor
public class HostileNPC extends NPC {
    private int health;
    private int strength;

    public HostileNPC(String id, String name, String description, List<Item> items, int health, int strength) {
        super(id, name, description, items);
        this.health = health;
        this.strength = strength;
    }

    @Override
    public void interact(Game game) {
    }

    /**
     * Determines if this NPC is detectable by the EMF reader.
     * * @return Always true for HostileNPCs.
     */
    @Override
    public boolean isDetectableByEmf() {
        return true;
    }
}
