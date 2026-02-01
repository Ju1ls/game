package cz.jull.command.type;

import cz.jull.Game;
import cz.jull.command.Command;
import cz.jull.command.PostCommandActionType;
import cz.jull.models.locations.Side;
import cz.jull.models.npc.FriendlyNPC;
import cz.jull.models.npc.NPC;
import lombok.Getter;

import java.util.Optional;

/**
 * Command implementation to initiate a conversation with a friendly NPC.
 */
public class TalkCommand extends Command {
    @Getter
    private final String name = "talk";

    /**
     * Attempts to start a conversation.
     * @param args Arguments passed by the user.
     * @param game The main game instance.
     * @return {@link PostCommandActionType#NONE}.
     */
    @Override
    public PostCommandActionType execute(String[] args, Game game) {
        if (game.getFightManager().isFighting()) {
            System.out.println("You can't talk while fighting!");
            return PostCommandActionType.NONE;
        }

        Side currentSide = game.getPlayer().getCurrentSide();
        if (currentSide == null || currentSide.getNpcs() == null) {
            System.out.println("There is no one here.");
            return PostCommandActionType.NONE;
        }

        Optional<NPC> npcOpt = currentSide.getNpcs().stream()
                .filter(n -> n instanceof FriendlyNPC)
                .findFirst();

        if (npcOpt.isPresent()) {
            npcOpt.get().interact(game);
        } else {
            System.out.println("There is no one friendly to talk to.");
        }
        return PostCommandActionType.NONE;
    }
}
