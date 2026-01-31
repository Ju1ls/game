package cz.jull;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        try {
            game.startGame();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}