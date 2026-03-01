package cz.jull.json_loader;

import cz.jull.models.locations.Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameDataTest {

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