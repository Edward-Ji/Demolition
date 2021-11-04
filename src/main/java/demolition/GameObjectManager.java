package demolition;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Manages all game objects. It handles game object creation and deletion. The
 * class includes various static helper methods for updating, rendering,
 * locating game objects etc.
 */
public class GameObjectManager {
    /**
     * Contains all game objects to be tracked in the game grid. All game objects in
     * this list is updated and drawn every frame.
     */
    private List<GameObject> allGameObjects = new LinkedList<>();
    /**
     * Contains game objects to be added this frame.
     */
    private List<GameObject> addGameObjects = new LinkedList<>();
    /**
     * Contains game objects to be deleted this frame.
     */
    private List<GameObject> delGameObjects = new LinkedList<>();

    /*
     * Add game object immediately after the update stage of the game loop.
     */
    public void add(GameObject gameObject) {
        addGameObjects.add(gameObject);
    }

    /*
     * Delete game object immediately after the update stage of the game loop.
     */
    public void del(GameObject gameObject) {
        delGameObjects.add(gameObject);
    }

    /**
     * Clears all game objects and pending add/deletion job.
     */
    public void clearAll() {
        allGameObjects.clear();
        addGameObjects.clear();
        delGameObjects.clear();
    }

    /**
     * Checks for object collision, calls both objects' {@link GameObject#onCollide}
     * for every collision. The method then calls each game object's
     * {@link GameObject#update} method. Lastly, the method updates the game object
     * list.
     */
    public void updateAll() {
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
    public void drawAll() {
        List<GameObject> sortedGameObjects = new LinkedList<>(allGameObjects);
        sortedGameObjects
                .sort(Comparator.<GameObject>comparingInt(obj -> obj.getGridY()).thenComparing(obj -> obj.getLayer()));
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
    public List<GameObject> atPos(int gridX, int gridY) {
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
    public boolean inField(int gridX, int gridY) {
        return 0 <= gridX && gridX < App.GRID_WIDTH && 0 <= gridY && gridY < App.GRID_HEIGHT;
    }
}
