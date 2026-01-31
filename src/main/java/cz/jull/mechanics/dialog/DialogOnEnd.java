package cz.jull.mechanics.dialog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface DialogOnEnd {
    record Continue(Dialog nextDialog) implements DialogOnEnd {
    }

    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    class AskQuestion implements DialogOnEnd {
        private final String question;
        private final Answer[] answers;
        private int typingSpeed = 10;

        public record Answer(String answer, Dialog dialog) {
        }
    }
}
