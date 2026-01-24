package cz.jull;

import cz.jull.json_loader.GameData;
import lombok.Getter;

import java.io.IOException;

public class Game {
    @Getter
    private Player player;

    @Getter
    private GameData gameData;

    public void startGame() {

    }

    public void loadGame() throws IOException {
        gameData = GameData.loadGameDataFromResources();
    }
}
