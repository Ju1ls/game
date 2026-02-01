package cz.jull.mechanics.dialog;

public interface DialogOnEnd {
    record Continue(Dialog nextDialog) implements DialogOnEnd {
    }

    record AskQuestion(String question, DialogOnEnd.AskQuestion.Answer[] answers) implements DialogOnEnd {
        public record Answer(String answer, Dialog dialog) {
        }
    }
}
