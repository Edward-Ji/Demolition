package demolition;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.List;
import processing.core.PImage;

public class GameObject {

    public static enum Layer {
        BACKGROUND, // Wall, Goal
        DEFAULT, // Player, Enemy, Bomb
        FOREGROUND; // Explosion
    }

    protected App app;

    protected PImage sprite;

    protected int gridX;
    protected int gridY;

    private Layer layer;

    private List<GameObject> collidedLastFrame = new ArrayList<>();

    public GameObject(App app, PImage sprite, int gridX, int gridY) {
        this(app, sprite, gridX, gridY, Layer.DEFAULT);
    }

    public GameObject(App app, PImage sprite, int gridX, int gridY, Layer layer) {
        this.app = app;
        this.sprite = sprite;
        this.gridX = gridX;
        this.gridY = gridY;
        this.layer = layer;
    }

    public boolean blocksMovement() {
        return false;
    }

    protected void update() {
    }

    private void onCollide(List<GameObject> others) {
        for (GameObject other : others) {
            if (!collidedLastFrame.contains(other)) {
                onCollideTrigger(other);
            }
        }
        collidedLastFrame = others;
    }

    protected void onCollideTrigger(GameObject other) {
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
                    gameObject.onCollide(
                            collidedGameObjects.stream().filter(obj -> obj != gameObject).collect(Collectors.toList()));
                }
            }
        }
        for (GameObject gameObject : allGameObjects) {
            gameObject.update();
        }
    }

    public static void drawAll(List<GameObject> allGameObjects) {
        List<GameObject> sortedGameObjects = new ArrayList<>(allGameObjects);
        sortedGameObjects.sort(Comparator.comparing(obj -> obj.layer));
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
