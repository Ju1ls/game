package cz.jull.models.locations;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST;

    /**
     * Gets the opposite direction.
     * @return Opposite direction.
     */
    public Direction getOpposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
        };
    }

    /**
     * Converts String to enum.
     * @param direction The String that's being converted.
     * @return Enumized String.
     * @throws IllegalArgumentException If the provided string is not a valid direction.
     */
    public static Direction fromString(String direction) throws IllegalArgumentException{
        return switch (direction) {
            case "north" -> NORTH;
            case "south" -> SOUTH;
            case "east" -> EAST;
            case "west" -> WEST;
            default -> throw new IllegalArgumentException("Unexpected value: " + direction);
        };
    }
}
