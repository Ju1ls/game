package cz.jull.mechanics.dialog;

import cz.jull.Game;
import cz.jull.models.npc.FriendlyNPC;
import lombok.Getter;

/**
 * Manages the state and flow of conversations between the {@link cz.jull.Player} and {@link FriendlyNPC}s.
 */
public class DialogManager {
    @Getter
    private FriendlyNPC currentNpc;
    private Dialog currentDialog;

    /**
     * Checks if the player is currently engaged in a valid dialogue session.
     * @return true if both an NPC and a dialog node are present; false otherwise.
     */
    public boolean isDialogActive() {
        return currentNpc != null && currentDialog != null;
    }

    /**
     * Initiates a new conversation session.
     * @param game The main game instance.
     * @param npc The friendly NPC to talk to.
     * @param rootDialog The starting point (root) of the dialogue tree.
     */
    public void startDialog(Game game, FriendlyNPC npc, Dialog rootDialog) {
        currentNpc = npc;
        transitionTo(game, rootDialog);
    }

    /**
     * Terminate the current conversation and resets the manager state.
     */
    public void stopDialog() {
        if (currentNpc != null) {
            System.out.println("You stopped talking to " + currentNpc.getName() + ".");
        }
        currentNpc = null;
        currentDialog = null;
    }

    /**
     * Processes a player's choice in response to a question.
     * @param game The main game instance.
     * @param index The index of the answer chosen by the player.
     */
    public void answer(Game game, int index) {
        if (!isDialogActive()) {
            System.out.println("You are not in a conversation.");
            return;
        }

        if (currentDialog.getOnEnd() instanceof DialogOnEnd.AskQuestion question) {
            int arrayIndex = index - 1;

            if (arrayIndex >= 0 && arrayIndex < question.answers().length) {
                DialogOnEnd.AskQuestion.Answer selected = question.answers()[arrayIndex];
                System.out.println(">>" + selected.answer());
                transitionTo(game, selected.dialog());
            } else {
                System.out.println("Invalid option number.");
            }
        } else {
            System.out.println("There is no question to answer here.");

            if (currentDialog.getOnEnd() instanceof DialogOnEnd.Continue(Dialog nextDialog)) {
                transitionTo(game, nextDialog);
            }
        }
    }

    /**
     * Transitions the conversation to a new dialog.
     * @param game The main game instance.
     * @param next The next {@link Dialog} to display.
     */
    private void transitionTo(Game game, Dialog next) {
        currentDialog = next;

        printCurrentState();

        if (currentDialog.getOnStartAction() != null) {
            currentDialog.getOnStartAction().accept(game);
        }

        if (currentDialog.getOnEnd() instanceof DialogOnEnd.Continue(Dialog nextDialog)) {
            transitionTo(game, nextDialog);
        } else if (currentDialog.getOnEnd() == null) {
            currentNpc = null;
            currentDialog = null;
        }
    }

    /**
     * Renders the current dialogue state to the console, including the NPC's name,
     * the dialogue text, and any available multiple-choice answers.
     */
    private void printCurrentState() {
        System.out.println("------------------------------------------------");
        System.out.println("\u001B[36m" + currentNpc.getName() + "\u001B[0m" + ": " + currentDialog.getText());

        if (currentDialog.getOnEnd() instanceof DialogOnEnd.AskQuestion question) {
            for (int i = 0; i < question.answers().length; i++) {
                System.out.println((i + 1) + ". " + question.answers()[i].answer());
            }
        }
        System.out.println("------------------------------------------------");
    }
}
