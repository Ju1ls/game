package cz.jull.mechanics.dialog;

/**
 * Represents the action to take when a specific dialog sequence concludes.
 *
 * @author Julie Šefl
 */
public interface DialogOnEnd {

    /**
     * A dialog resolution that automatically proceeds to the next dialog node
     * without requiring player input.
     * @param nextDialog The subsequent {@link Dialog} to be displayed.
     */
    record Continue(Dialog nextDialog) implements DialogOnEnd {
    }

    /**
     * A dialog resolution that prompts the player with a question and provides
     * a set of possible answers, branching the conversation based on their choice.
     * @param question The question or prompt displayed to the player.
     * @param answers  An array of possible {@link Answer}s the player can choose from.
     */
    record AskQuestion(String question, DialogOnEnd.AskQuestion.Answer[] answers) implements DialogOnEnd {

        /**
         * Represents a single player-selectable response to a question.
         * @param answer The text of the player's response option.
         * @param dialog The {@link Dialog} to trigger if the player selects this answer.
         */
        public record Answer(String answer, Dialog dialog) {
        }
    }
}
