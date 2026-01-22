package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;

import cz.jull.models.locations.Location;
import cz.jull.models.locations.Side;
import lombok.Getter;

public class GoCommand extends Command {

    @Getter
    private final String name = "go";

    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        String direction = args[0].toLowerCase();
        Location currentLoc = game.getPlayer().getCurrentLocation();

        switch (direction) {
            case "north" -> {
                Side northSide = currentLoc.getSides().get("north");
                game.getPlayer().setCurrentSide(northSide);
            }
            case "south" -> {
                Side southSide = currentLoc.getSides().get("south");
                game.getPlayer().setCurrentSide(southSide);
            }
            case "east" -> {
                Side eastSide = currentLoc.getSides().get("east");
                game.getPlayer().setCurrentSide(eastSide);
            }
            case "west" -> {
                Side westSide = currentLoc.getSides().get("west");
                game.getPlayer().setCurrentSide(westSide);
            }
            default -> {
                System.out.println("There is no side like that."); //TODO pozdeji smazu System.out.println(); a vymenim za neco jineho
            }
        }
        return PostCommandActionType.NONE;
    }
}
