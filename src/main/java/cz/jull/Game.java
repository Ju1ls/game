package cz.jull;

import cz.jull.json_loader.GameData;
import cz.jull.models.locations.Location;
import lombok.Getter;

import java.util.List;

public class Game {
    @Getter
    private Player player;

    @Getter
    private GameData gameData = new GameData();

    public void startGame() {

    }

    public void loadGame() {
        GameData.loadGameDataFromResources();
    }
}
