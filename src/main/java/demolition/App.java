package demolition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class App extends PApplet {

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    public static final int GRID_SIZE = 32;
    public static final int UI_HEIGHT = 64;

    public static final int GRID_WIDTH = 15;
    public static final int GRID_HEIGHT = 13;

    // Path to resources
    private static final String resourcePath = "src/main/resources/";
    private static final String fontPath = resourcePath + "PressStart2P-Regular.ttf";

    // Resources hash map
    public HashMap<String, PImage> staticSprites = new HashMap<>();
    public HashMap<String, List<List<PImage>>> animatedSprites = new HashMap<>();

    // Path to configuration file
    private static final String configPath = "config.json";

    // Configuration object
    private JSONObject config;

    // Game attributes
    private int lives;
    private int levelCount;
    private int frameLeft;

    public Player player;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        // Set frame per second
        frameRate(FPS);

        // Load and set text font
        textFont(createFont(fontPath, 14));

        // Load resources
        loadResources();

        // Load level configuration json file and first level
        config = loadJSONObject(configPath);
        lives = config.getInt("lives");
        loadLevel();
    }

    public void loadResources() {
        File resourceDir = new File(resourcePath);
        for (File resourceFile : resourceDir.listFiles()) {
            if (resourceFile.isFile()) {
                if (resourceFile.getName().endsWith(".png")) {
                    String spriteName = resourceFile.getName();
                    spriteName = spriteName.substring(0, spriteName.lastIndexOf('.'));
                    staticSprites.put(spriteName, loadImage(resourceFile.getPath()));
                }
            } else {
                List<List<PImage>> typeSprites = new ArrayList<>();
                File[] resourceDirs = resourceFile.listFiles();
                Arrays.sort(resourceDirs);
                for (File spritesDir : resourceDirs) {
                    List<PImage> sprites = new ArrayList<>();
                    File[] spriteFiles = spritesDir.listFiles();
                    Arrays.sort(spriteFiles);
                    for (File spriteFile : spriteFiles) {
                        sprites.add(loadImage(spriteFile.getPath()));
                    }
                    typeSprites.add(sprites);
                }
                animatedSprites.put(resourceFile.getName(), typeSprites);
            }
        }
    }

    public void loadLevel() {
        JSONArray levelArray = (JSONArray) config.get("levels");

        if (levelCount >= levelArray.size()) {
            // Win condition met
            gameOverScreen("YOU WIN");
            return;
        }

        GameObject.clearAll();

        JSONObject levelObject = (JSONObject) levelArray.getJSONObject(levelCount);
        String levelPath = levelObject.getString("path");
        Integer levelTime = levelObject.getInt("time");

        int gridX = 0;
        int gridY = 0;
        try (Scanner scanner = new Scanner(new File(levelPath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (char c : line.toCharArray()) {
                    GameObject gameObject = null;
                    switch (c) {
                    case 'B':
                        gameObject = Wall.brokenWall(this, gridX, gridY);
                        break;
                    case 'G':
                        gameObject = new Goal(this, staticSprites.get("goal"), gridX, gridY);
                        break;
                    case 'P':
                        gameObject = new Player(this, animatedSprites.get("player"), gridX, gridY);
                        player = (Player) gameObject;
                        break;
                    case 'R':
                        gameObject = new Enemy(this, animatedSprites.get("red_enemy"), gridX, gridY);
                        break;
                    case 'W':
                        gameObject = Wall.solidWall(this, gridX, gridY);
                        break;
                    case 'Y':
                        gameObject = new Enemy(this, animatedSprites.get("yellow_enemy"), gridX, gridY);
                        break;
                    }
                    gridX++;
                }
                gridX = 0;
                gridY++;
            }
        } catch (FileNotFoundException e) {
            gameOverScreen(e.getClass().getName() + e.toString());
        }

        frameLeft = levelTime * FPS;
    }

    public void draw() {
        if (frameLeft == 0 || lives == 0) {
            gameOverScreen("GAME OVER");
            return;
        }
        GameObject.updateAll();

        background(254, 134, 0);
        drawUI();
        drawField();
        GameObject.drawAll();

        frameLeft--;
    }

    private void drawUI() {
        pushStyle();
        imageMode(CENTER);
        textAlign(CENTER, CENTER);

        image(staticSprites.get("life_icon"), 30, UI_HEIGHT / 2);
        text(lives, WIDTH * 1 / 4, UI_HEIGHT / 2);
        image(staticSprites.get("clock_icon"), WIDTH / 2 + 30, UI_HEIGHT / 2);
        text(frameLeft / FPS, WIDTH * 3 / 4, UI_HEIGHT / 2);

        popStyle();
    }

    public void drawField() {
        for (int x = 0; x < WIDTH; x += GRID_SIZE) {
            for (int y = UI_HEIGHT; y < HEIGHT; y += GRID_SIZE) {
                image(staticSprites.get("field"), x, y);
            }
        }
    }

    public void nextLevel() {
        levelCount++;
        loadLevel();
    }

    public void loseOneLife() {
        lives--;
        loadLevel();
    }

    public void keyPressed() {
        if (key == CODED) {
            switch (keyCode) {
            case LEFT:
                player.control(Direction.LEFT);
                break;
            case RIGHT:
                player.control(Direction.RIGHT);
                break;
            case UP:
                player.control(Direction.UP);
                break;
            case DOWN:
                player.control(Direction.DOWN);
                break;
            }
        } else if (key == ' ') {
            new Bomb(this, animatedSprites.get("bomb"), player.gridX, player.gridY);
        }
    }

    public void gameOverScreen(String content) {
        background(254, 134, 0);
        textAlign(CENTER, CENTER);
        text(content, WIDTH / 2, HEIGHT / 2);
        noLoop();
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
