package cz.jull.mechanics.dialog;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class Dialog {
    private int typingSpeed = 10;
    private final String text;
    private final DialogOnEnd onEnd;
}
