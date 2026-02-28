package cz.jull.models.locations;

import cz.jull.models.Item;
import cz.jull.models.npc.NPC;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a specific side within a larger {@link Location}.
 */
@Getter
@Setter
@NoArgsConstructor
public class Side {
    private Location neighbor;
    private List<NPC> npcs;
    private List<Item> items;

    public Side(Location neighbor, List<NPC> npcs, List<Item> items) {
        this.neighbor = neighbor;
        this.npcs = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Side: \n" +
                "Location near: " + (neighbor != null ? neighbor.getName() : "none") + "\n" +
                "Npcs: " + (!npcs.isEmpty() ? npcs : "none") + "\n"+
                "Items: " + (!items.isEmpty() ? items : "none");
    }
}
