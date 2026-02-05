package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.Response;
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
     * @return {@link Response}
     */
    @Override
    public Response execute(String[] args, Game game) {
        if (args.length == 0) {
            return new Response("specify the option number");
        }

        try {
            int index = Integer.parseInt(args[0]);
            game.getDialogManager().answer(game, index);
        } catch (NumberFormatException e) {
            return new Response("invalid number format");
        }
        return new Response();
    }
}
