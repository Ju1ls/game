package cz.jull.models.locations;

import cz.jull.models.Item;
import cz.jull.models.npc.NPC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter

public class Side {
    private String neighbor;
    private List<NPC> npcs;
    private List<Item> items;

    public Side(String neighbor, List<NPC> npcs, List<Item> items) {
        this.neighbor = neighbor;
        this.npcs = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Side{" +
                "neighbor='" + neighbor + '\'' +
                ", npcs=" + npcs +
                ", items=" + items +
                '}';
    }
}
