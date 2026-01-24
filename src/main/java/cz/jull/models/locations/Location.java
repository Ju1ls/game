package cz.jull.models.locations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import cz.jull.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.EnumMap;
import java.util.List;

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
    private List<Item> items_unlocked;
    private EnumMap<Direction, Side> sides;

    @Override
    public String toString() {
        return "Location{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", locked=" + locked +
                ", items_unlocked=" + items_unlocked +
                ", sides=" + sides +
                '}';
    }
}
