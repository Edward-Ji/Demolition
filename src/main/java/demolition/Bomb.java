package demolition;

import java.util.List;
import processing.core.PImage;

public class Bomb extends AnimatedGameObject {

    private static int BLAST_DISTANCE = 2;

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
    public void destroy() {
        super.destroy();
        new Explosion(app, app.staticSprites.get("explosion_centre"), gridX, gridY);
        direction: for (Direction direction : Direction.values()) {
            PImage sprite;
            if (direction.getDeltaX() == 0) {
                sprite = app.staticSprites.get("explosion_vertical");
            } else {
                sprite = app.staticSprites.get("explosion_horizontal");
            }
            for (int dist = 1; dist <= BLAST_DISTANCE; dist++) {
                int newGridX = gridX + direction.getDeltaX() * dist;
                int newGridY = gridY + direction.getDeltaY() * dist;
                for (GameObject gameObject : GameObject.atPos(app.allGameObjects, newGridX, newGridY)) {
                    if (!gameObject.isBreakable()) {
                        continue direction;
                    }
                }
                new Explosion(app, sprite, newGridX, newGridY);
                for (GameObject gameObject : GameObject.atPos(app.allGameObjects, newGridX, newGridY)) {
                    if (gameObject.blocksMovement()) {
                        continue direction;
                    }
                }
            }
        }
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
