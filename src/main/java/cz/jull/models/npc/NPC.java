package cz.jull.models.npc;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.jull.json_loader.NPCDeserializer;
import cz.jull.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonDeserialize(using = NPCDeserializer.class)
public abstract class NPC {
    private String id;
    private String name;
    private String description;
    private List<Item> items;

    public NPC(String id, String name, String description, List<Item> items) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.items = new ArrayList<>();
    }

    public abstract void interact();

    @Override
    public String toString() {
        return "NPC{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", items=" + items +
                '}';
    }
}
