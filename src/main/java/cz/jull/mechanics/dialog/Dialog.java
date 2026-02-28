package cz.jull.mechanics.dialog;

import cz.jull.Game;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

/**
 * Represents a single step within a conversation tree.
 *
 * @author Julie Šefl
 */
@RequiredArgsConstructor
@Getter
public class Dialog {
    private final String text;
    private final DialogOnEnd onEnd;

    private Consumer<Game> onStartAction;

    public Dialog(String text, DialogOnEnd onEnd, Consumer<Game> onStartAction) {
        this.text = text;
        this.onEnd = onEnd;
        this.onStartAction = onStartAction;
    }
}
