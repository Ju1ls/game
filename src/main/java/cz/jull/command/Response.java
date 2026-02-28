package cz.jull.command;

/**
 * Represents the result of executing a command in the game.
 * @param type  The {@link PostCommandActionType} indicating what the engine should do next.
 * @param value An optional payload or message associated with the response.
 * @author Julie Šefl
 */
public record Response(PostCommandActionType type, String value) {

    /**
     * Constructs an empty Response.
     * Defaults the action type to {@link PostCommandActionType#NONE} and the value to {@code null}.
     */
    public Response() {
        this(PostCommandActionType.NONE, null);
    }

    /**
     * Constructs a Response with a specific string value but no special action.
     * Defaults the action type to {@link PostCommandActionType#NONE}.
     * @param value The string payload or message to be returned.
     */
    public Response(String value) {
        this(PostCommandActionType.NONE, value);
    }

    /**
     * Constructs a Response with a specific action type but no string value.
     * Defaults the string value to {@code null}.
     * @param type The {@link PostCommandActionType} indicating the next engine action.
     */
    public Response(PostCommandActionType type) {
        this(type, null);
    }
}
