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

public class Loader {

    // Path to all resources and configurations
    private static final String resourcePath = "src/main/resources/";
    private static final String fontPath = resourcePath + "PressStart2P-Regular.ttf";
    private static final String configPath = "config.json";

    // Resources hash map
    public HashMap<String, PImage> staticSprites = new HashMap<>();
    public HashMap<String, List<List<PImage>>> animatedSprites = new HashMap<>();

    // Configuration object
    private JSONObject config;

    private App app;

    Loader(App app) {
        this.app = app;
    }

    public void loadFont() {
        app.textFont(app.createFont(fontPath, 14));
    }

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

    public PImage getStaticSprite(String name) {
        return staticSprites.get(name);
    }

    public List<List<PImage>> getAnimatedSprite(String name) {
        return animatedSprites.get(name);
    }

    public void loadConfig() {
        config = app.loadJSONObject(configPath);
    }

    public int getLives() {
        return config.getInt("lives");
    }

    public int getMaxLevel() {
        return config.getJSONArray("levels").size();
    }

    public int loadLevel(int levelCount) {
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
            app.exit();
            return -1;
        }

        return levelObject.getInt("time");
    }

}
