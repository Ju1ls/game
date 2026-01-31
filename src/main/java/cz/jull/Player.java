package cz.jull;

import cz.jull.models.Item;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

@Getter

public class Player {
    @Setter
    private Location currentLocation;
    @Setter
    private Side currentSide;

    private int health = 100;
    private int oxygen = 100;
    private int mentalHealth = 100;
    private int detectorBatteryLevel = 100;

    public void setHealth(int health) throws Exception {
        if (health < 0) {
            throw new Exception("health cant have negative value");
        }
        this.health = health;
    }

    public void setOxygen(int oxygen) throws Exception {
        if (oxygen < 0) {
            throw new Exception("oxygen cant have negative value");
        }
        this.oxygen = oxygen;
    }

    public void setMentalHealth(int mentalHealth) throws Exception {
        if (mentalHealth < 0) {
            throw new Exception("mental health cant have negative value");
        }
        this.mentalHealth = mentalHealth;
    }

    public void setDetectorBatteryLevel(int detectorBatteryLevel) throws Exception {
        if (detectorBatteryLevel < 0) {
            throw new Exception("battery level cant have negative value");
        }
        this.detectorBatteryLevel = detectorBatteryLevel;
    }

    @Setter
    private ScheduledFuture<?> holdingBreathTask;

    private final List<Item> inventory = new ArrayList<>();

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }
}
