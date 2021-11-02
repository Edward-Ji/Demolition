package demolition;

import processing.core.PImage;

public class Explosion extends GameObject {

    private int countdown = (int) (0.5 * App.FPS);

    public Explosion(App app, PImage sprite, int gridX, int gridY) {
        super(app, sprite, gridX, gridY);
    }

    @Override
    public void update() {
        countdown--;
        if (countdown == 0) {
            destroy();
        }
    }

    @Override
    public void onCollide(GameObject other) {
        if (other.isBreakable()) {
            other.destroy();
        }
    }
}
