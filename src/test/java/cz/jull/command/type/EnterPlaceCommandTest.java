package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.PostCommandActionType;
import cz.jull.command.Response;
import cz.jull.models.Item;
import cz.jull.models.locations.Direction;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link EnterPlaceCommand} class.
 * This class validates the complex logic required to move a player from one
 * {@link Location} to another, including neighbor detection, locking/unlocking
 * mechanics, and correct directional arrival.
 *
 * @author Julie Šefl
 */
class EnterPlaceCommandTest {

    private EnterPlaceCommand enterCommand;
    private Game dummyGame;
    private Location neighborLocation;
    private Side northSide;
    private Side southSide;

    /**
     * Sets up a two-room mini-world before each test.
     * Room A (Street) has a North exit leading to Room B (Tunnel).
     * Room B has a South entrance, mirroring the exit from Room A.
     */
    @BeforeEach
    void setUp() {
        enterCommand = new EnterPlaceCommand();
        dummyGame = new Game();

        Location startLocation = new Location();
        startLocation.setName("Street");

        northSide = new Side();
        northSide.setNpcs(new ArrayList<>());
        northSide.setItems(new ArrayList<>());

        EnumMap<Direction, Side> startSides = new EnumMap<>(Direction.class);
        startSides.put(Direction.NORTH, northSide);
        startLocation.setSides(startSides);

        neighborLocation = new Location();
        neighborLocation.setId("loc_tunnel");
        neighborLocation.setName("Tunnel");
        neighborLocation.setLocked(false);

        southSide = new Side();
        southSide.setNpcs(new ArrayList<>());
        southSide.setItems(new ArrayList<>());

        EnumMap<Direction, Side> neighborSides = new EnumMap<>(Direction.class);
        neighborSides.put(Direction.SOUTH, southSide);
        neighborLocation.setSides(neighborSides);

        northSide.setNeighbor(neighborLocation);

        dummyGame.getPlayer().setCurrentLocation(startLocation);
        dummyGame.getPlayer().setCurrentSide(northSide);
    }

    /**
     * Verifies that attempting to enter a side with no neighbor returns
     * the "wall" error message.
     */
    @Test
    void execute_NoNeighbor_ReturnsWallMessage() {
        northSide.setNeighbor(null);

        Response response = enterCommand.execute(new String[0], dummyGame);

        assertEquals("You can't go here, there is a wall.", response.value());
    }

    /**
     * Verifies that locked locations block entry if the player
     * does not possess the required items in their inventory.
     */
    @Test
    void execute_LocationIsLockedWithoutItems_ReturnsLockedMessage() {
        neighborLocation.setLocked(true);
        Item key = new Item();
        key.setId("item_key");

        neighborLocation.setItemsUnlocked(List.of(key));

        Response response = enterCommand.execute(new String[0], dummyGame);

        assertEquals("Location is locked.", response.value());
        assertTrue(neighborLocation.isLocked());
    }

    /**
     * Verifies that having the correct items automatically unlocks a location
     * and allows the player to enter successfully.
     */
    @Test
    void execute_LocationIsLockedWithRequiredItems_EntersSuccessfully() {
        neighborLocation.setLocked(true);
        Item key = new Item();
        key.setId("item_key");
        neighborLocation.setItemsUnlocked(List.of(key));

        dummyGame.getPlayer().addItemToInventory(key);

        Response response = enterCommand.execute(new String[0], dummyGame);

        assertFalse(neighborLocation.isLocked(), "Location should become unlocked");
        assertTrue(response.value().contains("You entered: Tunnel"));
        assertEquals(neighborLocation, dummyGame.getPlayer().getCurrentLocation());
    }

    /**
     * Validates that upon entering a new location, the player is correctly
     * placed on the side opposite to their departure direction.
     * (e.g., exiting North should land the player at the South side of the next room).
     */
    @Test
    void execute_SuccessfulMove_CalculatesArrivalSideCorrectly() {
        neighborLocation.setDescription("A dark underground passage.");

        Response response = enterCommand.execute(new String[0], dummyGame);

        assertNotNull(response.value(), "Response should not be null");
        assertTrue(response.value().contains("You entered: Tunnel"), "Should announce the new location name");
        assertTrue(response.value().contains("A dark underground passage."), "Should display the location description");
        assertEquals(PostCommandActionType.NONE, response.type(), "Success move should have type NONE (unless victory)");

        assertEquals(neighborLocation, dummyGame.getPlayer().getCurrentLocation());
        assertEquals(southSide, dummyGame.getPlayer().getCurrentSide(),
                "Player should arrive at the side opposite to the one they entered from");
    }

    /**
     * Tests the special case for the Bunker.
     * Verifies that entering the bunker location triggers the victory state.
     */
    @Test
    void execute_EnteringBunker_ReturnsVictory() {
        neighborLocation.setId("loc_bunker");
        neighborLocation.setName("Bunker");

        Response response = enterCommand.execute(new String[0], dummyGame);

        assertEquals(PostCommandActionType.VICTORY, response.type());
    }
}