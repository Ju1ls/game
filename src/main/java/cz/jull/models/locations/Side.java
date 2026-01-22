package cz.jull.models.locations;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.jull.models.Item;
import cz.jull.models.npc.NPC;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Side {
    @JsonIgnore
    private Location neighbor;

    @JsonProperty("neighbor")
    private String neighborId;
    private List<NPC> npcs;
    private List<Item> items;

    public Side(Location neighbor, List<NPC> npcs, List<Item> items) {
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
