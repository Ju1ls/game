package cz.jull.json_loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.jull.models.locations.Location;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * A container class representing the static game data loaded from JSON configuration files.
 *
 * @author Julie Šefl
 */
@Getter
@Setter
public class GameData {

    private List<Location> locations;

    /**
     * Loads the game configuration from the "game_data.json" resource file.
     * @return The populated GameData object.
     * @throws IOException If the file cannot be read or the JSON is invalid.
     */
    public static GameData loadGameDataFromResources() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        InputStream is = GameData.class.getResourceAsStream("/game_data.json");
        return mapper.readValue(is, GameData.class);
    }
}

