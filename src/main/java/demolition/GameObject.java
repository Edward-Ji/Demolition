package demolition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import processing.core.PImage;

public class GameObject {

    public static enum Layer {
        BACKGROUND, // Wall, Goal
        DEFAULT, // Enemy, Bomb
        FOREGROUND; // Player, Explosion
    }

    protected App app;

    protected PImage sprite;

    protected int gridX;
    protected int gridY;

    private Layer layer;

    public GameObject(App app, PImage sprite, int gridX, int gridY) {
        this(app, sprite, gridX, gridY, Layer.DEFAULT);
    }

    public GameObject(App app, PImage sprite, int gridX, int gridY, Layer layer) {
        this.app = app;
        this.sprite = sprite;
        this.gridX = gridX;
        this.gridY = gridY;
        this.layer = layer;
        app.allGameObjects.add(this);
    }

    public void destroy() {
        app.destroy(this);
    }

    public boolean blocksMovement() {
        return false;
    }

    protected void update() {
    }

    protected void onCollide(GameObject other) {
    }

    protected void draw() {
        app.image(sprite, getDrawX(), getDrawY());
    }

    protected int getDrawX() {
        return gridX * App.GRID_SIZE;
    }

    protected int getDrawY() {
        return gridY * App.GRID_SIZE + App.UI_HEIGHT - (sprite.height - App.GRID_SIZE);
    }

    public static void updateAll(List<GameObject> allGameObjects) {
        for (int gridX = 0; gridX < App.GRID_WIDTH; gridX++) {
            for (int gridY = 0; gridY < App.GRID_HEIGHT; gridY++) {
                List<GameObject> collidedGameObjects = atPos(allGameObjects, gridX, gridY);
                for (GameObject gameObject : collidedGameObjects) {
                    for (GameObject otherGameObject : collidedGameObjects) {
                        if (gameObject != otherGameObject) {
                            gameObject.onCollide(otherGameObject);
                        }
                    }
                }
            }
        }
        for (GameObject gameObject : allGameObjects) {
            gameObject.update();
        }
    }

    public static void drawAll(List<GameObject> allGameObjects) {
        List<GameObject> sortedGameObjects = new ArrayList<>(allGameObjects);
        sortedGameObjects.sort(Comparator.<GameObject>comparingInt(obj -> obj.gridY).thenComparing(obj -> obj.layer));
        for (GameObject gameObject : sortedGameObjects) {
            gameObject.draw();
        }
    }

    public static List<GameObject> atPos(List<GameObject> allGameObjects, int gridX, int gridY) {
        List<GameObject> gameObjects = new ArrayList<>();
        for (GameObject gameObject : allGameObjects) {
            if (gameObject.gridX == gridX && gameObject.gridY == gridY) {
                gameObjects.add(gameObject);
            }
        }
        return gameObjects;
    }

    public static boolean inField(int gridX, int gridY) {
        return 0 <= gridX && gridX < App.GRID_WIDTH && 0 <= gridY && gridY < App.GRID_HEIGHT;
    }
}