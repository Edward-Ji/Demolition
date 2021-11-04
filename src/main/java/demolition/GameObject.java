package demolition;

import processing.core.PImage;

/**
 * Base class for all game objects. It handles game object update logic and
 * rendering.
 *
 * <br>
 * <strong>Event loop stages</strong>
 * <ol>
 * <li>Update collision - checks if two objects is in the same position. Calls
 * object {@link #onCollide} method on both objects if two collides.</li>
 * <li>Update - calls object {@link #update} method</li>
 * <li>Draw - draws the game object on application</li>
 * </ol>
 */
public abstract class GameObject {

    /**
     * Represents the rendering order.
     */
    public static enum Layer {
        /** Wall, Goal, Bomb */
        BACKGROUND,

        /** Enemy, Explosion */
        DEFAULT,

        /** Player */
        PLAYER;
    }

    /**
     * Reference to application.
     */
    protected App app;

    /**
     * Sprite to render.
     */
    protected PImage sprite;

    /**
     * The number of columns from left in game grid (starting from 0).
     */
    protected int gridX;
    /**
     * The number of rows from top in game grid (starting from 0).
     */
    protected int gridY;

    /**
     * Decides the rendering order.
     *
     * @see Layer
     */
    private Layer layer;

    /**
     * Indicates if the game object is destroyed.
     */
    private boolean destroyed;

    /**
     * Constructs a game object in default layer and tracks it.
     *
     * @param app    reference to application
     * @param sprite sprite to render
     * @param gridX  x position in grid
     * @param gridY  y position in grid
     * @see demolition.Loader#getStaticSprite
     */
    public GameObject(App app, PImage sprite, int gridX, int gridY) {
        this(app, sprite, gridX, gridY, Layer.DEFAULT);
    }

    /**
     * Constructs a game object and tracks it.
     *
     * @param app    reference to application
     * @param sprite sprite to render
     * @param gridX  x position in grid
     * @param gridY  y position in grid
     * @param layer  render order layer
     * @see demolition.Loader#getStaticSprite
     */
    public GameObject(App app, PImage sprite, int gridX, int gridY, Layer layer) {
        this.app = app;
        this.sprite = sprite;
        this.gridX = gridX;
        this.gridY = gridY;
        this.layer = layer;
        app.getManager().add(this);
    }

    /**
     * Add game object to the list of game objects to be deleted after the next
     * update stage.
     */
    public void destroy() {
        app.getManager().del(this);
        destroyed = true;
    }

    /**
     * Getter for if the game object is marked as destoryed.
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Getter for the number of colums from left in game grid (starting from 0).
     *
     * @return x position in grid
     */
    protected int getGridX() {
        return gridX;
    }

    /**
     * Getter for the number of rows from top in game grid (starting from 0).
     *
     * @return y position in grid
     */
    protected int getGridY() {
        return gridY;
    }

    /**
     * Getter for the reder order layer of this game object.
     *
     * @return y position in grid
     */
    protected Layer getLayer() {
        return layer;
    }

    /**
     * Returns if this game object blocks movement of other game objects. This is
     * readonly and defaults to <code>false</code>. Override this method to change
     * this attribute.
     *
     * @return if this game object blocks movement of other game objects.
     */
    public boolean blocksMovement() {
        return false;
    }

    /**
     * Returns if this game object can be broken by other game objects. This is
     * readonly and defaults to <code>false</code>. Override this method to change
     * this attribute.
     *
     * @return if this game object can be broken by other game objects.
     */
    public boolean isBreakable() {
        return false;
    }

    /**
     * Called in the update stage of the event loop. Override this method for object
     * logic (e.g. timed destory or movement).
     */
    protected void update() {
    }

    /**
     * Called in the collision stage of the event loop. The other object in the
     * collision is passed as the only argument. Note that this method is called on
     * both objects in a collision.
     *
     * @param other the other object in the collision
     */
    protected void onCollide(GameObject other) {
    }

    /**
     * Draws the object's sprite at its current position on the application surface.
     */
    protected void draw() {
        app.image(sprite, getDrawX(), getDrawY());
    }

    /**
     * Returns x position that the image should be drawn in pixels. This assumes
     * that the application draw mode is default.
     *
     * @return x position that the image should be drawn in pixels
     */
    protected int getDrawX() {
        return gridX * App.GRID_SIZE;
    }

    /**
     * Returns y position that the image should be drawn in pixels. This assumes
     * that the application draw mode is default.
     *
     * @return y position that the image should be drawn in pixels
     */
    protected int getDrawY() {
        return gridY * App.GRID_SIZE + App.UI_HEIGHT - (sprite.height - App.GRID_SIZE);
    }
}
