package cz.jull.models.npc;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import cz.jull.Game;
import cz.jull.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * The abstract base class representing a Non-Playable Character (NPC) in the game.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FriendlyNPC.class, name = "friendly"),
        @JsonSubTypes.Type(value = HostileNPC.class, name = "hostile")
})
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

    /**
     * Defines the behavior triggered when the player initiates an interaction with this NPC.
     * @param game The current {@link Game} instance and state.
     */
    public abstract void interact(Game game);

    /**
     * Determines whether the player's EMF detector can pick up a signal from this entity.
     * @return {@code true} if the NPC triggers the EMF detector, {@code false} otherwise.
     */
    public abstract boolean isDetectableByEmf();

    @Override
    public String toString() {
        return "Name: " + name;
    }
}
