package demolition;

import java.util.List;
import processing.core.PImage;

public class Enemy extends AnimatedGameObject {

    private static final int moveInterval = App.FPS;

    private int moveFrameCount;

    public Enemy(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        super(app, sprites, gridX, gridY);
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public void update() {
        if (moveFrameCount == moveInterval) {
            while (movementBlocked()) {
                turn(getDirection().next());
            }
            move();
            moveFrameCount = 0;
        }

        moveFrameCount++;
    }

    @Override
    public void onCollide(GameObject other) {
        if (other instanceof Player) {
            app.loseOneLife();
        }
    }

    public static Enemy redEnemy(App app, int gridX, int gridY) {
        return new Enemy(app, app.getLoader().getAnimatedSprite("red_enemy"), gridX, gridY);
    }

    public static Enemy yellowEnemy(App app, int gridX, int gridY) {
        return new Enemy(app, app.getLoader().getAnimatedSprite("yellow_enemy"), gridX, gridY);
    }
}
