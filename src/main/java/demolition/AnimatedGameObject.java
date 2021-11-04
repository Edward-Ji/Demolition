package demolition;

import java.util.List;
import processing.core.PImage;

/**
 * Base class for animated game objects. It has all the features of normal game
 * object, but also speicial features for animations and directional actions.
 *
 * <br>
 * <strong>Event loop stages</strong>
 * <ol>
 * <li>Update collision - checks if two objects is in the same position. Calls
 * object {@link #onCollide} method on both objects if two collides.</li>
 * <li>Update - calls object {@link #update} method</li>
 * <li><strong>Animation update</strong> - updates the sprite for animtion</li>
 * <li>Draw - draws the game object on application</li>
 * </ol>
 */
public abstract class AnimatedGameObject extends GameObject {

    /**
     * The direction the game object is facing.
     */
    private Direction direction = Direction.DOWN;
    /**
     * A 2D list of sprites for directional animation.
     */
    private List<List<PImage>> sprites;
    /**
     * The number of game frames between each animation frame.
     */
    private int frameInterval = (int) (0.2 * App.FPS);
    /**
     * The number of game frames since the start of animation.
     */
    private int frameCount;

    private AnimatedGameObject(App app, PImage sprite, int gridX, int gridY) {
        super(app, sprite, gridX, gridY);
    }

    private AnimatedGameObject(App app, PImage sprite, int gridX, int gridY, Layer layer) {
        super(app, sprite, gridX, gridY, layer);
    }

    /**
     * Constructs an animated game object in default layer.
     *
     * @param app     reference to application
     * @param sprites a 2D list of sprites to render
     * @param gridX   x position in grid
     * @param gridY   y position in grid
     * @see demolition.Loader#getAnimatedSprite
     */
    public AnimatedGameObject(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        this(app, sprites, gridX, gridY, Layer.DEFAULT);
    }

    /**
     * Constructs an animated game object.
     *
     * @param app     reference to application
     * @param sprites a 2D list of sprites to render
     * @param gridX   x position in grid
     * @param gridY   y position in grid
     * @param layer   render order layer
     * @see demolition.Loader#getAnimatedSprite
     */
    public AnimatedGameObject(App app, List<List<PImage>> sprites, int gridX, int gridY, Layer layer) {
        this(app, (PImage) null, gridX, gridY, layer);
        this.sprites = sprites;
    }

    /**
     * Getter for the direction the object is facing.
     *
     * @return the direction the object is facing
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Setter for the number of game frames between each animation frame.
     *
     * @param value the number of game frames between each animation frame
     */
    protected void setFrameInterval(int value) {
        frameInterval = value;
    }

    /**
     * Updates the sprite for animation. The method gets the list of sprites of the
     * game object's direction. It increments the frame count and updates the sprite
     * as per frame interval.
     *
     * @see setFrameInterval
     */
    private void updateSprite() {
        List<PImage> directionSprites = sprites.get(direction.getAlphabetOrder());
        sprite = directionSprites.get(frameCount / frameInterval);
        frameCount = (frameCount + 1) % (directionSprites.size() * frameInterval);
    }

    /**
     * Updates the sprite for animation and draw on the application.
     */
    @Override
    protected void draw() {
        updateSprite();
        super.draw();
    }

    /**
     * Setter for the direction the object is facing.
     *
     * @param newDirection the direction the object is facing.
     */
    protected void turn(Direction newDirection) {
        direction = newDirection;
    }

    /**
     * Returns if there is a game object one cell in the direction this game object
     * is facing that blocks movement. The position also has to be in the grid.
     *
     * @return if the game object can move in the direction it is facing
     */
    protected boolean movementBlocked() {
        return movementBlocked(direction);
    }

    /**
     * Returns if there is a game object one cell in the given direction that blocks
     * movement. The position also has to be in the grid.
     *
     * @param direction a direction to check
     * @return if the game object can move in the given direction
     */
    protected boolean movementBlocked(Direction direction) {
        int newGridX = gridX + direction.getDeltaX();
        int newGridY = gridY + direction.getDeltaY();

        if (!app.getManager().inField(newGridX, newGridY)) {
            return true;
        }

        for (GameObject gameObject : app.getManager().atPos(newGridX, newGridY)) {
            if (gameObject.blocksMovement()) {
                return true;
            }
        }

        return false;
    }

    /**
     * Move in the direction the game object is facing by a distance of 1.
     */
    protected void move() {
        if (movementBlocked()) {
            return;
        }

        int newGridX = gridX + direction.getDeltaX();
        int newGridY = gridY + direction.getDeltaY();

        gridX = newGridX;
        gridY = newGridY;
    }
}
