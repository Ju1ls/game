package cz.jull.json_loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;
import lombok.Setter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class GameData {

    private List<Location> locations;

    /**
     * Loads and initializes the game world data from the classpath resource {@code /game_data.json}.
     * @return The fully populated {@link GameData} instance, or {@code null} if loading fails.
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
            System.err.println("Failed to loadGameDataFromResources game data:"); //TODO ill replace with exception
            e.printStackTrace();
        }
        return data;
    }
}

