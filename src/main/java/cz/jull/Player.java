package cz.jull;

import cz.jull.models.Item;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

public class Player {
    @Setter
    @Getter
    private Location currentLocation;

    @Setter
    @Getter
    private Side currentSide;

    @Setter
    @Getter
    private Item equippedItem;

    @Getter
    private int health = 100;

    private int oxygen = 100;
    @Getter
    private int mentalHealth = 100;
    @Getter
    private int detectorBatteryLevel = 100;

    @Setter
    @Getter
    private boolean emfHighFrequency = false;

    @Setter
    @Getter
    private boolean hasOxygenMask = false;

    @Setter
    @Getter
    private ScheduledFuture<?> holdingBreathTask;

    @Getter
    private final List<Item> inventory = new ArrayList<>();

    /**
     * Calculates damage based on the equipped item.
     */
    public int getAttackDamage() {
        if (equippedItem == null) {
            return 5;
        }

        return switch (equippedItem.getId()) {
            case "item_knife" -> 25;
            case "item_glass_shard" -> 15;
            default -> 5;
        };
    }

    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }

    public int getOxygen() {
        if (hasOxygenMask) {
            return 100;
        }
        return oxygen;
    }

    public void setHealth(int health) throws Exception {
        this.health = Math.max(0, health);
    }

    public void setOxygen(int oxygen) throws Exception {
        if (oxygen < 0) {
            throw new Exception("oxygen cant have negative value");
        }

        if (hasOxygenMask) {
            return;
        }
        this.oxygen = oxygen;
    }

    public void setMentalHealth(int mentalHealth) throws Exception {
        if (mentalHealth < 0) {
            throw new Exception("mental health cant have negative value");
        }
        this.mentalHealth = mentalHealth;
    }

    public void setDetectorBatteryLevel(int detectorBatteryLevel) {
        this.detectorBatteryLevel = Math.max(0, detectorBatteryLevel);
    }
}
