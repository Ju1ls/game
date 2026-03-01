package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Response;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.locations.Direction;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link GoCommand} class.
 * Validates the player's ability to rotate and face different directions within a location,
 * ensuring proper error handling for invalid inputs or missing directions.
 *
 * @author Julie Šefl
 */
class GoCommandTest {

    private GoCommand goCommand;
    private Game dummyGame;
    private Side northSide;

    /**
     * Sets up a controlled game environment with a dummy location and a single valid direction (North).
     * Initializes lists within the {@link Side} to prevent NullPointerExceptions during execution.
     */
    @BeforeEach
    void setUp() {
        goCommand = new GoCommand();
        dummyGame = new Game();

        Location dummyLocation = new Location();
        dummyLocation.setName("Crossroads");

        northSide = new Side();
        northSide.setNpcs(new ArrayList<>());
        northSide.setItems(new ArrayList<>());

        EnumMap<Direction, Side> sidesMap = new EnumMap<>(Direction.class);
        sidesMap.put(Direction.NORTH, northSide);
        dummyLocation.setSides(sidesMap);

        dummyGame.getPlayer().setCurrentLocation(dummyLocation);
    }

    /**
     * Verifies that the command prompts the user for a destination when no arguments are provided.
     */
    @Test
    void execute_NoArguments_ReturnsGoWhereMessage() {
        String[] args = new String[0];

        Response response = goCommand.execute(args, dummyGame);

        assertEquals("Go where?", response.value());
        assertEquals(PostCommandActionType.NONE, response.type());
    }

    /**
     * Verifies that the command handles gibberish or non-existent directions correctly
     * without crashing the game loop.
     */
    @Test
    void execute_InvalidDirection_ReturnsInvalidDirectionMessage() {
        String[] args = new String[]{"upwards"};

        Response response = goCommand.execute(args, dummyGame);

        assertEquals("Invalid direction: upwards", response.value());
        assertEquals(PostCommandActionType.NONE, response.type());
    }

    /**
     * Tests the scenario where a direction is valid (e.g., South) but the current
     * location does not have a side mapped to that direction.
     */
    @Test
    void execute_ValidDirectionButNoSideExists_ReturnsCannotGoWayMessage() {
        String[] args = new String[]{"south"};

        Response response = goCommand.execute(args, dummyGame);

        assertEquals("You can't go that way.", response.value());
        assertEquals(PostCommandActionType.NONE, response.type());
    }

    /**
     * Validates that a successful movement command updates the player's internal state
     * to the correct {@link Side} and returns a confirmation message.
     */
    @Test
    void execute_ValidDirectionAndSideExists_UpdatesPlayerSideAndReturnsSuccess() {
        String[] args = new String[]{"north"};

        Response response = goCommand.execute(args, dummyGame);

        assertTrue(response.value().contains("You headed north."), "Response should confirm the movement");
        assertEquals(PostCommandActionType.NONE, response.type());

        assertEquals(northSide, dummyGame.getPlayer().getCurrentSide(), "Player's current side should be updated to the North side");
    }
}