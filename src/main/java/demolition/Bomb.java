package demolition;

import java.util.List;
import processing.core.PImage;

public class Bomb extends AnimatedGameObject {

    public Bomb(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        super(app, sprites, gridX, gridY);
    }

    @Override
    public void update() {

    }

    @Override
    public void turn(Direction newDirection) {
        // Left empty intentionally
        // Bomb should not be able to turn
    }

}
