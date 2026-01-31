package cz.jull.command;

import cz.jull.Game;
import cz.jull.command.type.*;

import java.util.HashSet;
import java.util.Set;

public class CommandManager {
    private final Set<Command> commands = new HashSet<>();

    public void runCommand(String fullString, Game game) {
        for (Command command : commands) {
            if (!fullString.startsWith(command.getName())) {
                continue;
            }

            String[] parts = fullString.replaceFirst(command.getName(), "").trim().split(" ");

            PostCommandActionType type = command.execute(parts, game);
            switch (type) {
                case NONE -> {
                }
                case DEAD -> {
                    System.out.println("U are dead");
                }
                case EXIT -> {
                    System.exit(0);
                }
            }
        }
    }

    public void initialization() {
        commands.add(new AnswerCommand());
        commands.add(new AttackCommand());
        commands.add(new ClueCommand());
        commands.add(new DefenseCommand());
        commands.add(new EnterPlaceCommand());
        commands.add(new ExitCommand());
        commands.add(new GoCommand());
        commands.add(new HelpCommand());
        commands.add(new HoldBreathCommand());
        commands.add(new InventoryCommand());
        commands.add(new SearchCommand());
        commands.add(new StopDialogCommand());
        commands.add(new TakeItemCommand());
        commands.add(new TalkCommand());
        commands.add(new UseItemCommand());
    }
}
