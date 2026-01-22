package cz.jull;

import cz.jull.models.Item;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Player {
    private Location currentLocation;
    private Side currentSide;
    private final List<Item> inventory = new ArrayList<>();

    public void addItemToInventory(Item item) {

    }

    public void removeItemFromInventory(Item item) {

    }
}
