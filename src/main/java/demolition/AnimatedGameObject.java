package demolition;

import java.util.List;
import processing.core.PImage;

public abstract class AnimatedGameObject extends GameObject {

    private Direction direction = Direction.DOWN;
    private List<List<PImage>> sprites;
    private int frameInterval = (int) (0.2 * App.FPS);
    private int frameCount;

    private AnimatedGameObject(App app, PImage sprite, int gridX, int gridY) {
        super(app, sprite, gridX, gridY);
    }

    private AnimatedGameObject(App app, PImage sprite, int gridX, int gridY, Layer layer) {
        super(app, sprite, gridX, gridY, layer);
    }

    public AnimatedGameObject(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        this(app, sprites, gridX, gridY, Layer.DEFAULT);
    }

    public AnimatedGameObject(App app, List<List<PImage>> sprites, int gridX, int gridY, Layer layer) {
        this(app, (PImage) null, gridX, gridY, layer);
        this.sprites = sprites;
    }

    public Direction getDirection() {
        return direction;
    }

    protected void setFrameInterval(int value) {
        frameInterval = value;
    }

    private void updateSprite() {
        List<PImage> directionSprites = sprites.get(direction.getAlphabetOrder());
        sprite = directionSprites.get(frameCount / frameInterval);
        frameCount = (frameCount + 1) % (directionSprites.size() * frameInterval);
    }

    @Override
    protected void draw() {
        updateSprite();
        super.draw();
    }

    protected void turn(Direction newDirection) {
        direction = newDirection;
    }

    protected boolean movementBlocked() {
        int newGridX = gridX + direction.getDeltaX();
        int newGridY = gridY + direction.getDeltaY();

        if (!GameObject.inField(newGridX, newGridY)) {
            return true;
        }

        for (GameObject gameObject : GameObject.atPos(newGridX, newGridY)) {
            if (gameObject.blocksMovement()) {
                return true;
            }
        }

        return false;
    }

    protected boolean move() {
        if (movementBlocked()) {
            return false;
        }

        int newGridX = gridX + direction.getDeltaX();
        int newGridY = gridY + direction.getDeltaY();

        gridX = newGridX;
        gridY = newGridY;

        return true;
    }
}
