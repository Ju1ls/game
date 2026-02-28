package cz.jull.command;

/**
 * Defines the possible outcomes or state transitions required after a command is executed.
 *
 * @author Julie Šefl
 */
public enum PostCommandActionType {
    NONE,
    DEAD,
    VICTORY,
    EXIT
}
