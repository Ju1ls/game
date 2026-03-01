package cz.jull;

import cz.jull.models.Item;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Player} class.
 *
 *  @author Julie Šefl
 */
class PlayerTest extends Player {

    /**
     * Verifies that attack damage is calculated correctly based on the equipped item.
     * Tests standard items (knife, glass shard), unequipped state, and unknown items.
     */
    @Test
    void getAttackDamage_WithVariousItems_ReturnsExpectedDamage() {
        this.setEquippedItem(null);
        assertEquals(5, this.getAttackDamage(), "Damage should be 5 when no item is equipped");

        Item item_knife = new Item() {
            @Override
            public String getId() {
                return "item_knife";
            }
        };
        this.setEquippedItem(item_knife);
        assertEquals(25, this.getAttackDamage(), "Damage should be 25 when item_knife is equipped");

        Item item_glass_shard = new Item() {
            @Override
            public String getId() {
                return "item_glass_shard";
            }
        };
        this.setEquippedItem(item_glass_shard);
        assertEquals(15, this.getAttackDamage(), "Damage should be 15 when item_glass_shard is equipped");

        Item item_unknown = new Item() {
            @Override
            public String getId() {
                return "item_unknown";
            }
        };
        this.setEquippedItem(item_unknown);
        assertEquals(5, this.getAttackDamage(), "Damage should fallback to 5 for unknown items");
    }

    /**
     * Tests if an item is successfully added to the player's inventory list.
     */
    @Test
    void addItemToInventory_ValidItem_ItemIsAdded() {
        Item item = new Item();
        this.addItemToInventory(item);

        assertEquals(1, this.getInventory().size());
        assertTrue(this.getInventory().contains(item));
    }

    /**
     * Tests if a specific item is successfully removed from the player's inventory list.
     */
    @Test
    void removeItemFromInventory_ExistingItem_ItemIsRemoved() {
        Item item = new Item();

        this.addItemToInventory(item);
        this.removeItemFromInventory(item);

        assertTrue(this.getInventory().isEmpty());
    }

    /**
     * Verifies that the oxygen mask overrides the current oxygen level.
     * If the mask is active, the player should always behave as if oxygen is at 100%.
     */
    @Test
    void getOxygen_WithOxygenMask_ReturnsMaxOxygen() throws Exception {
        assertEquals(100, this.getOxygen(), "Initial oxygen should be 100");

        this.setHasOxygenMask(true);
        this.setOxygen(50);

        assertEquals(100, this.getOxygen(), "getOxygen should always return 100 when mask is true");
    }

    /**
     * Validates that setting a negative health value results in the health being
     * clamped to a minimum of 0.
     */
    @Test
    void setHealth_WithNegativeValue_ClampsToZero() {
        this.setHealth(50);
        assertEquals(50, this.getHealth());

        this.setHealth(-10);
        assertEquals(0, this.getHealth());
    }

    /**
     * Verifies that setting a negative oxygen value triggers an {@link Exception}
     * with the appropriate error message.
     */
    @Test
    void setOxygen_WithNegativeValue_ThrowsException() throws Exception {
        this.setHasOxygenMask(false);
        this.setOxygen(75);
        assertEquals(75, this.getOxygen());

        Exception exception = assertThrows(Exception.class, () -> this.setOxygen(-5));
        assertEquals("Oxygen can't have negative value.", exception.getMessage());
    }

    /**
     * Verifies that setting a negative mental health value triggers an {@link Exception}
     * with the appropriate error message.
     */
    @Test
    void setMentalHealth_WithNegativeValue_ThrowsException() throws Exception {
        this.setMentalHealth(80);
        assertEquals(80, this.getMentalHealth());

        Exception exception = assertThrows(Exception.class, () -> this.setMentalHealth(-20));
        assertEquals("Mental health can't have negative value.", exception.getMessage());
    }

    /**
     * Validates that setting a negative battery level for the detector results
     * in the level being clamped to a minimum of 0.
     */
    @Test
    void setDetectorBatteryLevel_WithNegativeValue_ClampsToZero() {
        this.setDetectorBatteryLevel(45);
        assertEquals(45, this.getDetectorBatteryLevel());

        this.setDetectorBatteryLevel(-15);
        assertEquals(0, this.getDetectorBatteryLevel());
    }
}