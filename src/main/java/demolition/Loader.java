package demolition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Scanner;
import processing.core.PImage;
import processing.data.JSONArray;
import processing.data.JSONObject;

/**
 * The class for loading resources and configuration files.
 */
public class Loader {

    /**
     * Path to all resource files, including images and font.
     */
    private static final String resourcePath = "src/main/resources/";
    /**
     * Path to the font file.
     */
    private static final String fontPath = resourcePath + "PressStart2P-Regular.ttf";

    /**
     * Path to the configuration file.
     */
    private String configPath;

    /**
     * A hashmap from non-animated game object type name to its corresponding image
     * file.
     */
    private HashMap<String, PImage> staticSprites = new HashMap<>();
    /**
     * A hashmap from animated game object type name to its corresponding image
     * files. The first list dimension represents direction and the second
     * represents frame.
     */
    private HashMap<String, List<List<PImage>>> animatedSprites = new HashMap<>();

    /**
     * Level configuration object. It includes player lives and the path and time
     * for each level.
     */
    private JSONObject config;

    /**
     * Reference to application object.
     */
    private App app;

    /**
     * The index of current level (first level is 0).
     */
    private int levelCount;

    public Loader(App app, String configPath) {
        this.app = app;
        this.configPath = configPath;
    }

    /**
     * Loads the default font and set as the text font for the application.
     */
    public void loadFont() {
        app.textFont(app.createFont(fontPath, 14));
    }

    /**
     * Automatically loads all the images from the resource directory. The directory
     * must contain static (non-animated) sprites in the first level, together with
     * the direcotry that contains all animated sprites. The animated sprites are
     * put into directories according to their directions. If a sprite has only one
     * direction, it still need to be put into a direcoty. The animated sprites are
     * loaded in alphabetical order.
     *
     * <br>
     * <strong>Example resource file structure</strong>
     *
     * <pre>
     * src/main/resources
     * ├── ...
     * ├── goal.png               → non-animated sprite
     * ├── life_icon.png          → UI icon
     * ├── player                 → animated sprites
     * │  ├── down                → animated sprites in one direction
     * │  │  ├── player_down1.png → first frame of animated sprites in one direction
     * │  │  ├── player_down2.png → second frame of animated sprites in one direction
     * │  │  ├── player_down3.png
     * │  │  └── player_down4.png
     * │  ├── left                → animated sprites in another direction
     * │  │  ├── player_left1.png → first frame of animated sprites in that other direction
     * |  |  ├── ...
     * ├── bomb
     * │  └── single              → animated sprites in only one direction
     * │     ├── bomb1.png
     * │     ├── bomb2.png
     * |     ├── ...
     * </pre>
     */
    public void loadResources() {
        File resourceDir = new File(resourcePath);
        for (File resourceFile : resourceDir.listFiles()) {
            if (resourceFile.isFile()) {
                if (resourceFile.getName().endsWith(".png")) {
                    String spriteName = resourceFile.getName();
                    spriteName = spriteName.substring(0, spriteName.lastIndexOf('.'));
                    staticSprites.put(spriteName, app.loadImage(resourceFile.getPath()));
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
                        sprites.add(app.loadImage(spriteFile.getPath()));
                    }
                    typeSprites.add(sprites);
                }
                animatedSprites.put(resourceFile.getName(), typeSprites);
            }
        }
    }

    /**
     * Getter for static sprites hashmap. Returns the static sprite by the given
     * name.
     *
     * @param name the name of the sprite in directory
     * @return a processing image
     */
    public PImage getStaticSprite(String name) {
        return staticSprites.get(name);
    }

    /**
     * Getter for animated sprites hashmap. Returns a animated sprites in a 2D list
     * by the given name.
     *
     * @param name the name of the sprite in directory
     * @return a 2D list of processing image
     */
    public List<List<PImage>> getAnimatedSprite(String name) {
        return animatedSprites.get(name);
    }

    /**
     * Loads the configuration file.
     */
    public void loadConfig() {
        config = app.loadJSONObject(configPath);
        app.setLives(config.getInt("lives"));
    }

    /**
     * Loads the current game level. This method reads the game level file and
     * create relevant game objects with their position in the level file. If the
     * file is invalid, the application exits. The characters representation is as
     * follows:
     * <ul>
     * <li>B - broken wall</li>
     * <li>G - goal</li>
     * <li>P - player</li>
     * <li>R - red enemy</li>
     * <li>W - solid wall</li>
     * <li>Y - yellow enemy</li>
     * </ul>
     */
    public void loadLevel() {
        JSONArray levelArray = (JSONArray) config.get("levels");

        GameObject.clearAll();

        JSONObject levelObject = (JSONObject) levelArray.getJSONObject(levelCount);
        String levelPath = levelObject.getString("path");

        int gridX = 0;
        int gridY = 0;
        try (Scanner scanner = new Scanner(new File(levelPath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (char c : line.toCharArray()) {
                    switch (c) {
                    case 'B':
                        Wall.brokenWall(app, gridX, gridY);
                        break;
                    case 'G':
                        new Goal(app, gridX, gridY);
                        break;
                    case 'P':
                        app.player = new Player(app, gridX, gridY);
                        break;
                    case 'R':
                        Enemy.redEnemy(app, gridX, gridY);
                        break;
                    case 'W':
                        Wall.solidWall(app, gridX, gridY);
                        break;
                    case 'Y':
                        Enemy.yellowEnemy(app, gridX, gridY);
                        break;
                    }
                    gridX++;
                }
                gridX = 0;
                gridY++;
            }
        } catch (FileNotFoundException e) {
            app.screen = App.Screen.ERROR;
        }

        app.setFrameLeft(levelObject.getInt("time") * App.FPS);
    }

    /**
     * Increments the level counter and load the next level. If this is the last
     * level, set application screen to win screen.
     */
    public void nextLevel() {
        levelCount++;
        if (levelCount < config.getJSONArray("levels").size()) {
            loadLevel();
        } else {
            app.screen = App.Screen.WIN;
        }
    }
}
