package cz.jull.command;

import cz.jull.Game;
import cz.jull.Player;
import cz.jull.models.locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandManagerTest {

    private CommandManager commandManager;
    private Game dummyGame;

    @BeforeEach
    void setUp() {
        commandManager = new CommandManager();

        dummyGame = new Game();

        Player dummyPlayer = new Player();
        Location dummyLocation = new Location();
        dummyLocation.setName("Test Location");

        dummyGame.getPlayer().setCurrentLocation(dummyLocation);
    }

    @Test
    void runCommand_NullOrEmptyInput_ReturnsFalse() {
        assertFalse(commandManager.runCommand(null, dummyGame), "Null input should return false");
        assertFalse(commandManager.runCommand("", dummyGame), "Empty input should return false");
        assertFalse(commandManager.runCommand("   ", dummyGame), "Whitespace-only input should return false");
    }

    @Test
    void runCommand_InvalidCommand_ReturnsFalse() {
        boolean result = commandManager.runCommand("dance", dummyGame);
        assertFalse(result, "Unknown commands should simply return false (game continues)");
    }

    @Test
    void runCommand_HelpCommand_ReturnsFalse() {
        boolean result = commandManager.runCommand("help", dummyGame);
        assertFalse(result, "Help command returns NONE, so the manager should return false to keep the game running");
    }

    @Test
    void runCommand_ExitCommand_ReturnsTrue() {
        boolean result = commandManager.runCommand("exit", dummyGame);
        assertTrue(result, "Exit command returns EXIT, so the manager should return true to end the game loop");
    }

    @Test
    void runCommand_WithArguments_ParsesCorrectly() {
        boolean result = commandManager.runCommand("throw argument1 argument2", dummyGame);
        assertFalse(result);
    }
}