package demolition;

import processing.core.PApplet;

/**
 * The main application class that handles screen, user interface and calling
 * rendering methods.
 */
public class App extends PApplet {

    /**
     * Represents the three screens the application can display.
     */
    public static enum Screen {
        /**
         * Represents the game screen, that is the palyer is still playing the game.
         */
        GAME,
        /**
         * Represents the win screen, set after the player has beaten the last level.
         */
        WIN,
        /**
         * Represents the game over screen, set after the player health or time left
         * reaches 0.
         */
        LOST;
    }

    /**
     * Window size constants in pixels.
     */
    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    /**
     * Frame per second constant.
     */
    public static final int FPS = 60;

    /**
     * The constant size of each grid cell in pixels.
     */
    public static final int GRID_SIZE = 32;
    /**
     * The constant height of UI elements (i.e. player lives left and level
     * countdown timer).
     */
    public static final int UI_HEIGHT = 64;
    /**
     * Game grid size constants in number of cells.
     */
    public static final int GRID_WIDTH = 15;
    public static final int GRID_HEIGHT = 13;

    /**
     * Resource and configuration file loader.
     *
     * @see Loader
     */
    private Loader loader;

    /**
     * How many lives do the player have.
     */
    private int lives;
    /**
     * How much time the player have to beat the current level in frames (i.e. time
     * in seconds times FPS).
     */
    private int frameLeft;

    public Player player;

    /**
     * The current screen to be displayed
     *
     * @see Screen
     */
    public Screen screen = Screen.GAME;

    /**
     * Getter for resource and configuration file loader.
     *
     * @return resource and configuration file loader
     */
    public Loader getLoader() {
        return loader;
    }

    /**
     * Setter for the number of lives the player have at the start.
     *
     * @param lives the number of lives the player have at the start
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Setter for the time the player have to beat the current level.
     *
     * @param frameLeft the time the player have to beat the current level
     */
    public void setFrameLeft(int frameLeft) {
        this.frameLeft = frameLeft;
    }

    /**
     * Set window width.
     */
    @Override
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * Set frame rate. Load all fonts, sprites, configuration and the first level.
     */
    @Override
    public void setup() {
        // Set frame per second.
        frameRate(FPS);

        // Load all resources and configurations.
        loader = new Loader(this);
        loader.loadFont();
        loader.loadResources();
        loader.loadConfig();
        loader.loadLevel();
    }

    /**
     * Draw the screen to be displayed.
     */
    @Override
    public void draw() {
        background(254, 134, 0);
        switch (screen) {
        case GAME:
            gameScreen();
            break;
        case LOST:
            textScreen("GAME OVER");
            break;
        case WIN:
            textScreen("YOU WON");
            break;
        }
        frameLeft--;
    }

    /**
     * Display the game screen. Check for losing condition update all game object
     * and render them.
     */
    private void gameScreen() {
        if (frameLeft == 0 || lives == 0) { // Check losing condition
            screen = Screen.LOST;
        }

        // Update all game object
        GameObject.updateAll();

        // Draw user interface elements
        pushStyle();
        imageMode(CENTER);
        textAlign(CENTER, CENTER);
        image(loader.getStaticSprite("life_icon"), 30, UI_HEIGHT / 2);
        text(lives, WIDTH * 1 / 4, UI_HEIGHT / 2);
        image(loader.getStaticSprite("clock_icon"), WIDTH / 2 + 30, UI_HEIGHT / 2);
        text(frameLeft / FPS, WIDTH * 3 / 4, UI_HEIGHT / 2);
        popStyle();

        // Draw background
        for (int x = 0; x < WIDTH; x += GRID_SIZE) {
            for (int y = UI_HEIGHT; y < HEIGHT; y += GRID_SIZE) {
                image(loader.getStaticSprite("field"), x, y);
            }
        }
        // Draw all game objects
        GameObject.drawAll();
    }

    /**
     * Player loses on life and reload the current level.
     */
    public void loseOneLife() {
        lives--;
        loader.loadLevel();
    }

    /**
     * Handles key press as player movement and bomb placement.
     */
    @Override
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
            player.placeBomb();
        }
    }

    /**
     * Display a centered text with given string.
     *
     * @param text the text to be displayed in the middle.
     */
    public void textScreen(String text) {
        background(254, 134, 0);
        textAlign(CENTER, CENTER);
        text(text, WIDTH / 2, HEIGHT / 2);
    }

    /**
     * Program entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
