package cz.jull.mechanics.dialog;

import cz.jull.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Dialog {
    private int typingSpeed = 10;
    private final String text;
    private final DialogOnEnd onEnd;

    private Consumer<Game> onStartAction;
}
