package cz.jull.command;

public record Response(PostCommandActionType type, String value) {
    public Response() {
        this(PostCommandActionType.NONE, null);
    }

    public Response(String value) {
        this(PostCommandActionType.NONE, value);
    }

    public Response(PostCommandActionType type) {
        this(type, null);
    }
}
