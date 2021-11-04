package demolition;

/**
 * Represents directions. This class also includes some helper functions for
 * generating directions and calculating movement positions.
 */
public enum Direction {

    /**
     * The four valid directions.
     */
    DOWN(0, 1, 0), LEFT(-1, 0, 1), UP(0, -1, 3), RIGHT(1, 0, 2);

    /**
     * Delta in x position if an object moves in this direction.
     */
    private int deltaX;
    /**
     * Delta in y position if an object moves in this direction.
     */
    private int deltaY;
    /**
     * The index if the directions are sorted in alphabetical order. This is for
     * automatic sprite loading and rendering.
     */
    private int alphabetOrder;

    /**
     * Create a direction object.
     *
     * @param deltaX        delta in x position if an object moves in this direction
     * @param deltaY        delta in y position if an object moves in this direction
     * @param alphabetOrder index if the directions are sorted alphabetically
     */
    private Direction(int deltaX, int deltaY, int alphabetOrder) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.alphabetOrder = alphabetOrder;
    }

    /**
     * Returns the delta in x position if an object moves in this direction.
     *
     * @return x position delta
     */
    public int getDeltaX() {
        return deltaX;
    }

    /**
     * Returns the delta in y position if an object moves in this direction.
     *
     * @return y position delta
     */
    public int getDeltaY() {
        return deltaY;
    }

    /**
     * Returns the index if the directions are sorted in alphabetical order. This is
     * for automatic sprite loading and rendering.
     *
     * @return the index if the directions are sorted in alphabetical order
     */
    public int getAlphabetOrder() {
        return alphabetOrder;
    }

    /**
     * Returns the next direction if rotating clockwise. Used by the yellow enemy.
     *
     * @param direction a given direction
     * @return the next direction clockwise
     */
    public static Direction next(Direction direction) {
        return values()[(direction.ordinal() + 1) % values().length];
    }
}
