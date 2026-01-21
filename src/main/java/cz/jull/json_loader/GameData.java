package cz.jull.json_loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jull.models.locations.Location;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Represents the root container for all static game data loaded from JSON.
 *  * <p>
 *  * This class acts as both a data wrapper (holding the list of {@link Location} objects)
 *  * and a utility provider for loading that data from the application resources.
 *  * </p>
 */
@Getter
@Setter
public class GameData {

    /**
     * The list of all locations available in the game world.
     * Mapped from the "locations" array in the JSON file.
     */
    private List<Location> locations;

    /**
     * Loads the game data from the {@code /game_data.json} file located in the resource directory.
     * <p>
     * This method utilizes Jackson to deserialize the JSON content into a {@link GameData} instance.
     * If the file is not found or parsing fails, the error is printed to {@code System.err},
     * and this method returns {@code null}.
     * </p>
     *
     * @return A populated {@link GameData} object if successful; {@code null} if loading fails.
     */
    public static GameData loadGameDataFromResources() {
        ObjectMapper mapper = new ObjectMapper();
        GameData data = null;

        try (InputStream is = GameData.class.getResourceAsStream("/game_data.json")) {

            if (is == null) {
                throw new FileNotFoundException("'game_data.json' not found in src/main/resources/");
            }

            data = mapper.readValue(is, GameData.class);
        } catch (Exception e) {
            System.err.println("Failed to loadGameDataFromResources game data:");
            e.printStackTrace();
        }
        return data;
    }
}

