package cz.jull.command;

import cz.jull.Game;
import cz.jull.models.locations.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link CommandManager} class.
 * This class validates the parsing of user input and the handling of various
 * {@link PostCommandActionType} results to control the main game loop.
 *
 * @author Julie Šefl
 */
class CommandManagerTest {

    private CommandManager commandManager;
    private Game dummyGame;

    /**
     * Initializes the command manager and a game environment before each test.
     */
    @BeforeEach
    void setUp() {
        commandManager = new CommandManager();

        dummyGame = new Game();

        Location dummyLocation = new Location();
        dummyLocation.setName("Test Location");

        dummyGame.getPlayer().setCurrentLocation(dummyLocation);
    }

    /**
     * Verifies that null, empty, or whitespace-only strings do not crash the manager
     * and correctly return {@code false} to indicate the game loop should continue.
     */
    @Test
    void runCommand_NullOrEmptyInput_ReturnsFalse() {
        assertFalse(commandManager.runCommand(null, dummyGame), "Null input should return false");
        assertFalse(commandManager.runCommand("", dummyGame), "Empty input should return false");
        assertFalse(commandManager.runCommand("   ", dummyGame), "Whitespace-only input should return false");
    }

    /**
     * Verifies that entering a command that doesn't exist (e.g., "dance")
     * is handled gracefully and does not stop the game.
     */
    @Test
    void runCommand_InvalidCommand_ReturnsFalse() {
        boolean result = commandManager.runCommand("dance", dummyGame);
        assertFalse(result, "Unknown commands should simply return false (game continues)");
    }

    /**
     * Tests that informational commands like "help" return {@code false}.
     * This confirms the manager correctly interprets {@link PostCommandActionType#NONE}.
     */
    @Test
    void runCommand_HelpCommand_ReturnsFalse() {
        boolean result = commandManager.runCommand("help", dummyGame);
        assertFalse(result, "Help command returns NONE, so the manager should return false to keep the game running");
    }

    /**
     * Tests the "exit" command logic.
     * Verifies that the manager returns {@code true} when receiving an {@link PostCommandActionType#EXIT},
     * which signals the main loop to terminate.
     */
    @Test
    void runCommand_ExitCommand_ReturnsTrue() {
        boolean result = commandManager.runCommand("exit", dummyGame);
        assertTrue(result, "Exit command returns EXIT, so the manager should return true to end the game loop");
    }

    /**
     * Verifies that the manager can handle commands with multiple arguments
     * without crashing, even if the specific command isn't fully implemented
     * in the test scope.
     */
    @Test
    void runCommand_WithArguments_ParsesCorrectly() {
        boolean result = commandManager.runCommand("throw argument1 argument2", dummyGame);
        assertFalse(result);
    }
}