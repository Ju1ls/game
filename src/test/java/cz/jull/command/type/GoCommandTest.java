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

class GoCommandTest {

    private GoCommand goCommand;
    private Game dummyGame;
    private Side northSide;

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

    @Test
    void execute_NoArguments_ReturnsGoWhereMessage() {
        String[] args = new String[0];

        Response response = goCommand.execute(args, dummyGame);

        assertEquals("Go where?", response.value());
        assertEquals(PostCommandActionType.NONE, response.type());
    }

    @Test
    void execute_InvalidDirection_ReturnsInvalidDirectionMessage() {
        String[] args = new String[]{"upwards"};

        Response response = goCommand.execute(args, dummyGame);

        assertEquals("Invalid direction: upwards", response.value());
        assertEquals(PostCommandActionType.NONE, response.type());
    }

    @Test
    void execute_ValidDirectionButNoSideExists_ReturnsCannotGoWayMessage() {
        String[] args = new String[]{"south"};

        Response response = goCommand.execute(args, dummyGame);

        assertEquals("You can't go that way.", response.value());
        assertEquals(PostCommandActionType.NONE, response.type());
    }

    @Test
    void execute_ValidDirectionAndSideExists_UpdatesPlayerSideAndReturnsSuccess() {
        String[] args = new String[]{"north"};

        Response response = goCommand.execute(args, dummyGame);

        assertTrue(response.value().contains("You headed north."), "Response should confirm the movement");
        assertEquals(PostCommandActionType.NONE, response.type());

        assertEquals(northSide, dummyGame.getPlayer().getCurrentSide(), "Player's current side should be updated to the North side");
    }
}