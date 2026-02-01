package cz.jull.mechanics.dialog;

import cz.jull.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@RequiredArgsConstructor
@Getter
public class Dialog {
    private int typingSpeed = 10;
    private final String text;
    private final DialogOnEnd onEnd;

    private Consumer<Game> onStartAction;

    public Dialog(int typingSpeed, String text, DialogOnEnd onEnd, Consumer<Game> onStartAction) {
        this.typingSpeed = typingSpeed;
        this.text = text;
        this.onEnd = onEnd;
        this.onStartAction = onStartAction;
    }

    public Dialog(int typingSpeed, String text, DialogOnEnd onEnd) {
        this(typingSpeed, text, onEnd, null);
    }
}
