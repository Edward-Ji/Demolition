package demolition;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import processing.core.PImage;

/**
 * Base class for all game objects. It handles game object creation, update
 * logic, rendering and deletion. The class includes various static helper
 * methods for updating, rendering, locating game objects etc.
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
     * Contains all game objects to be tracked in the game grid. All game objects in
     * this list is updated and drawn every frame.
     */
    private static List<GameObject> allGameObjects = new LinkedList<>();
    /**
     * Contains game objects to be added this frame.
     */
    private static List<GameObject> addGameObjects = new LinkedList<>();
    /**
     * Contains game objects to be deleted this frame.
     */
    private static List<GameObject> delGameObjects = new LinkedList<>();

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
        addGameObjects.add(this);
    }

    /**
     * Add game object to the list of game objects to be deleted this frame.
     */
    public void destroy() {
        delGameObjects.add(this);
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

    /**
     * Clears all game objects and pending add/deletion job.
     */
    public static void clearAll() {
        allGameObjects.clear();
        addGameObjects.clear();
        delGameObjects.clear();
    }

    /**
     * Checks for object collision, calls both objects' {@link #onCollide} for every
     * collision. The method then calls each game object's {@link #update} method.
     * Lastly, the method updates the game object list.
     */
    public static void updateAll() {
        // Collision logic
        for (int gridX = 0; gridX < App.GRID_WIDTH; gridX++) {
            for (int gridY = 0; gridY < App.GRID_HEIGHT; gridY++) {
                List<GameObject> collidedGameObjects = atPos(gridX, gridY);
                for (GameObject gameObject : collidedGameObjects) {
                    for (GameObject otherGameObject : collidedGameObjects) {
                        if (gameObject != otherGameObject) {
                            gameObject.onCollide(otherGameObject);
                        }
                    }
                }
            }
        }

        // Call each object's update
        for (GameObject gameObject : allGameObjects) {
            gameObject.update();
        }

        // Maintain game object list
        allGameObjects.addAll(addGameObjects);
        addGameObjects.clear();
        allGameObjects.removeAll(delGameObjects);
        delGameObjects.clear();
    }

    /**
     * Calls the draw method of all game object sorted first by y position, then by
     * their render layers.
     */
    public static void drawAll() {
        List<GameObject> sortedGameObjects = new LinkedList<>(allGameObjects);
        sortedGameObjects.sort(Comparator.<GameObject>comparingInt(obj -> obj.gridY).thenComparing(obj -> obj.layer));
        for (GameObject gameObject : sortedGameObjects) {
            gameObject.draw();
        }
    }

    /**
     * Returns a list of game objects that is at the given position.
     *
     * @param gridX x position on grid
     * @param gridY y position on grid
     * @return a list of game objects at the given position
     */
    public static List<GameObject> atPos(int gridX, int gridY) {
        List<GameObject> gameObjects = new LinkedList<>();
        for (GameObject gameObject : allGameObjects) {
            if (gameObject.gridX == gridX && gameObject.gridY == gridY) {
                gameObjects.add(gameObject);
            }
        }
        return gameObjects;
    }

    /**
     * Checks if a given postion is valid on the game grid.
     *
     * @param gridX x position on grid
     * @param gridY y position on grid
     * @return if a given position is valid
     */
    public static boolean inField(int gridX, int gridY) {
        return 0 <= gridX && gridX < App.GRID_WIDTH && 0 <= gridY && gridY < App.GRID_HEIGHT;
    }
}
