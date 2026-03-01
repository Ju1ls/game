package cz.jull.models;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.mechanics.ScheduledTaskManager;
import cz.jull.models.locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest extends Item {
    private Player dummyPlayer;
    private Game dummyGame;

    @BeforeEach
    void setUpGameEnvironment() {
        dummyPlayer = new Player();

        Location dummyLocation = new Location();
        dummyPlayer.setCurrentLocation(dummyLocation);

        dummyGame = new Game() {
            @Override
            public Player getPlayer() {
                return dummyPlayer;
            }

            @Override
            public ScheduledTaskManager getScheduledTaskManager() {
                return new ScheduledTaskManager();
            }
        };

        this.setUsable(true);
    }

    @Test
    void useItem_WhenNotUsable() throws Exception {
        this.setUsable(false);
        this.setId("item_medkit");
        dummyPlayer.setHealth(50);

        this.useItem(dummyGame);

        assertEquals(50, dummyPlayer.getHealth());
    }

    @Test
    void useItem_EmfDetector() throws Exception {
        this.setId("item_emf_detector");
        dummyPlayer.setDetectorBatteryLevel(10);
        dummyPlayer.setEmfHighFrequency(false);

        this.useItem(dummyGame);

        assertEquals(8, dummyPlayer.getDetectorBatteryLevel());
        assertTrue(dummyPlayer.isEmfHighFrequency());

        dummyPlayer.setDetectorBatteryLevel(0);
        this.useItem(dummyGame);

        assertEquals(0, dummyPlayer.getDetectorBatteryLevel());
        assertTrue(dummyPlayer.isEmfHighFrequency());
    }

    @Test
    void useItem_OxygenMask() throws Exception {
        this.setId("item_oxygen_mask");
        assertFalse(dummyPlayer.isHasOxygenMask());

        this.useItem(dummyGame);

        assertFalse(this.isUsable(), "Oxygen mask should be marked unusable after activation");
        assertTrue(dummyPlayer.isHasOxygenMask(), "Player should now have the oxygen mask equipped");
    }

    @Test
    void useItem_Weapons() throws Exception {
        this.setId("item_knife"); // Works identically for item_glass_shard
        dummyPlayer.setHealth(100);

        dummyPlayer.setMentalHealth(50);
        this.useItem(dummyGame);
        assertEquals(this, dummyPlayer.getEquippedItem());
        assertEquals(100, dummyPlayer.getHealth(), "Health should not decrease if mental health > 30");

        dummyPlayer.setMentalHealth(25);
        this.useItem(dummyGame);
        assertEquals(90, dummyPlayer.getHealth());

        dummyPlayer.setMentalHealth(15);
        this.useItem(dummyGame);
        assertEquals(70, dummyPlayer.getHealth());

        dummyPlayer.setMentalHealth(5);
        this.useItem(dummyGame);
        assertEquals(0, dummyPlayer.getHealth());
    }

    @Test
    void useItem_Batteries() throws Exception {
        this.setId("item_batteries");
        dummyPlayer.setDetectorBatteryLevel(15);

        this.useItem(dummyGame);

        assertEquals(100, dummyPlayer.getDetectorBatteryLevel(), "Batteries should restore detector to 100");
    }

    @Test
    void useItem_Medkit() throws Exception {
        this.setId("item_medkit");
        dummyPlayer.setHealth(50);

        this.useItem(dummyGame);

        assertEquals(60, dummyPlayer.getHealth(), "Medkit should add 10 health");
    }

    @Test
    void useItem_DrugsAndAlcohol() throws Exception {
        this.setId("item_drugs"); // Logic is identical for item_alcohol
        dummyPlayer.setHealth(100);
        dummyPlayer.setMentalHealth(50);

        this.useItem(dummyGame);

        assertEquals(85, dummyPlayer.getHealth(), "Drugs should decrease health by 15");
        assertEquals(60, dummyPlayer.getMentalHealth(), "Drugs should increase mental health by 10");
    }

    @Test
    void testToString() {
        this.setName("Flashlight");
        String expected = " \nName: Flashlight\n";
        assertEquals(expected, this.toString(), "toString format should match perfectly");
    }
}