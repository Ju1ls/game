package cz.jull.json_loader;

import cz.jull.models.locations.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link GameData} class.
 * This class ensures that the game's static configuration (locations, items, etc.)
 * can be correctly parsed from the JSON resource files into Java objects.
 *
 * @author Julie Šefl
 */
class GameDataTest {

    /**
     * Verifies that the {@code game_data.json} file is correctly located in the resources
     * and that the Jackson {@code ObjectMapper} can map the JSON structure to a
     * {@link GameData} object without errors.
     * <p>
     * This test requires a valid {@code game_data.json} file to be present in
     * {@code src/test/resources}.
     */
    @Test
    void testLoadGameDataFromResources() {
        assertDoesNotThrow(() -> {
            GameData loadedData = GameData.loadGameDataFromResources();

            assertNotNull(loadedData, "GameData should be successfully instantiated from JSON");

            assertNotNull(loadedData.getLocations(), "The locations list should be mapped from the JSON");
            assertFalse(loadedData.getLocations().isEmpty(), "The locations list should not be empty");

            Location firstLocation = loadedData.getLocations().getFirst();
            assertEquals("Test Room", firstLocation.getName(), "The location name should match the test JSON file");
        }, "Loading the JSON file threw an exception. Make sure game_data.json exists in src/test/resources!");
    }
}