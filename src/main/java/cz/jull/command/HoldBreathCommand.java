package cz.jull.command;

import lombok.Getter;

public class HoldBreathCommand extends Command{
    @Getter
    private final String name = "hold breath";


    @Override
    public PostCommandActionType execute(String[] args) {
        return PostCommandActionType.NONE;
    }
}
