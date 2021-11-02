package demolition;

import java.util.List;
import processing.core.PImage;

public class Bomb extends AnimatedGameObject {

    private int countdown = 2 * App.FPS;

    public Bomb(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        super(app, sprites, gridX, gridY);
        setFrameInterval((int) (0.25 * App.FPS));
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }

    @Override
    public void update() {
        countdown--;
        if (countdown == 0) {
            destroy();
        }
    }

    @Override
    public void turn(Direction newDirection) {
        // Left empty intentionally
        // Bomb should not be able to turn
    }

}
