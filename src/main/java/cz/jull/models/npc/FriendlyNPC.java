package cz.jull.models.npc;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import cz.jull.models.Item;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@JsonDeserialize(using = JsonDeserializer.None.class)
public class FriendlyNPC extends NPC {
    public FriendlyNPC(String id, String name, String description, List<Item> items) {
        super(id, name, description, items);
    }

    @Override
    public void interact() {

    }
}
