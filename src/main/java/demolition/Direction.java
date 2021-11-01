package demolition;

public enum Direction {

    DOWN(0, 1, 0), LEFT(-1, 0, 1), UP(0, -1, 3), RIGHT(1, 0, 2);

    private int deltaX;
    private int deltaY;
    private int alphabetOrder;

    private Direction(int deltaX, int deltaY, int alphabetOrder) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.alphabetOrder = alphabetOrder;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public int getAlphabetOrder() {
        return alphabetOrder;
    }

    public Direction next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

}
