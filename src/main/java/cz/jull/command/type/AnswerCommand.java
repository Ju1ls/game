package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import lombok.Getter;

/**
 * Command implementation for answering questions during an active dialogue.
 */
public class AnswerCommand extends Command {
    @Getter
    private final String name = "answer";

    /**
     * Executes the answer command logic.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        if (args.length == 0) {
            System.out.println("specify the option number.");
            return PostCommandActionType.NONE;
        }

        try {
            int index = Integer.parseInt(args[0]);
            game.getDialogManager().answer(game, index);
        } catch (NumberFormatException e) {
            System.out.println("invalid number format");
        }
        return PostCommandActionType.NONE;
    }
}
