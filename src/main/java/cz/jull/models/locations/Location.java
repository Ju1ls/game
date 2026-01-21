package cz.jull.models.locations;

import cz.jull.models.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Location {
    private String id;
    private String name;
    private String description;
    private boolean locked;
    private List<Item> items_unlocked;
    private Map<String, Side> sides;

    public Location(String id, String name, String description, boolean locked, List<Item> items_unlocked, Map<String, Side> sides) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.locked = locked;
        this.items_unlocked = new ArrayList<>();
        this.sides = new HashMap<>();
    }

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
