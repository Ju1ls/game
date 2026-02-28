package cz.jull.models.locations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.jull.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.EnumMap;
import java.util.List;

/**
 * Represents a distinct geographical area within the game world.
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Location {
    private String id;
    private String name;
    private String description;
    private boolean locked;

    @JsonIdentityReference(alwaysAsId = true)
    @JsonProperty("items_unlocked")
    private List<Item> itemsUnlocked;

    private EnumMap<Direction, Side> sides;

    @Override
    public String toString() {
        return "Location: \n" +
                "Name: " + name + "\n"+
                "Description: " + description + "\n"+
                "Sides: " + sides;
    }
}
