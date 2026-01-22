package cz.jull.models.locations;

import cz.jull.models.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class Location {
    private String id;
    private String name;
    private String description;
    private boolean locked;
    private List<Item> items_unlocked;
    private Map<String, Side> sides;

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
