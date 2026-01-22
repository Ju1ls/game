package cz.jull.command;

import cz.jull.Game;
import cz.jull.Player;

public abstract class Command {
    public abstract String getName();
    public abstract PostCommandActionType execute(String[] args, Game game);
}
