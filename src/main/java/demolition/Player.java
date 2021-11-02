package demolition;

import java.util.List;
import processing.core.PImage;

public class Player extends AnimatedGameObject {

    static final int layer = 0;

    public Player(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        super(app, sprites, gridX, gridY, Layer.FOREGROUND);
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public void destroy() {
        app.loseOneLife();
    }

    public void control(Direction newDirection) {
        Direction oldDirection = getDirection();
        turn(newDirection);
        if (!move()) {
            turn(oldDirection);
        }
    }

}
