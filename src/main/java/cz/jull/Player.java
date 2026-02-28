package cz.jull;

import cz.jull.models.Item;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * Represents a player entity within the game.
 *
 * @author Julie Šefl
 */
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

    /**
     * Adds item to the players inventory.
     * @param item The item being added.
     */
    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    /**
     * Removes item from players inventory.
     * @param item The item being removed.
     */
    public void removeItemFromInventory(Item item) {
        inventory.remove(item);
    }

    /**
     * Retrieves the player's current oxygen level.
     * If the player has an oxygen mask, this always simulates a full oxygen tank.
     * @return The current oxygen level, or 100 if an oxygen mask is equipped.
     */
    public int getOxygen() {
        if (hasOxygenMask) {
            return 100;
        }
        return oxygen;
    }

    /**
     * Sets the player's health. The health value is clamped to ensure it does not drop below 0.
     * @param health The new health value to set.
     */
    public void setHealth(int health) {
        this.health = Math.max(0, health);
    }

    /**
     * Sets the player's oxygen level. If the player has an oxygen mask, the oxygen
     * level remains unaffected by this setter.
     * @param oxygen The new oxygen value to set.
     * @throws Exception If the provided oxygen value is negative.
     */
    public void setOxygen(int oxygen) throws Exception {
        if (oxygen < 0) {
            throw new Exception("Oxygen can't have negative value.");
        }

        if (hasOxygenMask) {
            return;
        }
        this.oxygen = oxygen;
    }

    /**
     * Sets the player's mental health level.
     * @param mentalHealth The new mental health value to set.
     * @throws Exception If the provided mental health value is negative.
     */
    public void setMentalHealth(int mentalHealth) throws Exception {
        if (mentalHealth < 0) {
            throw new Exception("Mental health can't have negative value.");
        }
        this.mentalHealth = mentalHealth;
    }

    /**
     * Sets the battery level for the player's detector. The battery level is clamped
     * to ensure it does not drop below 0.
     * @param detectorBatteryLevel The new battery level to set.
     */
    public void setDetectorBatteryLevel(int detectorBatteryLevel) {
        this.detectorBatteryLevel = Math.max(0, detectorBatteryLevel);
    }
}
