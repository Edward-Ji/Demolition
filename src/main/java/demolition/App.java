package demolition;

import processing.core.PApplet;

public class App extends PApplet {

    public static enum Screen {
        GAME, WIN, LOST;
    }

    public static final int WIDTH = 480;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;

    public static final int GRID_SIZE = 32;
    public static final int UI_HEIGHT = 64;

    public static final int GRID_WIDTH = 15;
    public static final int GRID_HEIGHT = 13;

    private Loader loader;

    // Game attributes
    private int lives;
    private int frameLeft;

    public Player player;

    public Screen screen = Screen.GAME;

    public Loader getLoader() {
        return loader;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setFrameLeft(int frameLeft) {
        this.frameLeft = frameLeft;
    }

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        // Set frame per second
        frameRate(FPS);

        // Load all resources and configurations
        loader = new Loader(this);
        loader.loadFont();
        loader.loadResources();
        loader.loadConfig();
        loader.loadLevel();
    }

    public void draw() {
        background(254, 134, 0);
        switch (screen) {
        case GAME:
            gameScreen();
            break;
        case LOST:
            finalScreen("GAME OVER");
            break;
        case WIN:
            finalScreen("YOU WON");
            break;
        }
        frameLeft--;
    }

    private void gameScreen() {
        if (frameLeft == 0 || lives == 0) {
            screen = Screen.LOST;
        }

        GameObject.updateAll();
        drawUI();
        GameObject.drawAll();
    }

    private void drawUI() {
        pushStyle();
        imageMode(CENTER);
        textAlign(CENTER, CENTER);

        image(loader.getStaticSprite("life_icon"), 30, UI_HEIGHT / 2);
        text(lives, WIDTH * 1 / 4, UI_HEIGHT / 2);
        image(loader.getStaticSprite("clock_icon"), WIDTH / 2 + 30, UI_HEIGHT / 2);
        text(frameLeft / FPS, WIDTH * 3 / 4, UI_HEIGHT / 2);
        popStyle();

        for (int x = 0; x < WIDTH; x += GRID_SIZE) {
            for (int y = UI_HEIGHT; y < HEIGHT; y += GRID_SIZE) {
                image(loader.getStaticSprite("field"), x, y);
            }
        }
    }

    public void loseOneLife() {
        lives--;
        loader.loadLevel();
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
            player.placeBomb();
        }
    }

    public void finalScreen(String content) {
        background(254, 134, 0);
        textAlign(CENTER, CENTER);
        text(content, WIDTH / 2, HEIGHT / 2);
    }

    public static void main(String[] args) {
        PApplet.main("demolition.App");
    }
}
