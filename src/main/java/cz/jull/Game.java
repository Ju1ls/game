package cz.jull;

import cz.jull.json_loader.GameData;
import cz.jull.models.locations.Location;

import java.util.List;

public class Game {
    private List<Location> locations;
    private Player player;


    public void startGame() {

    }

    public void loadGame() {
        GameData.loadGameDataFromResources();
    }
}
