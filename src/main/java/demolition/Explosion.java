package demolition;

import processing.core.PImage;

/**
 * Explosion destroys other game objects.
 */
public class Explosion extends GameObject {

    /**
     * The number of game frames before the explosion disappears.
     */
    private int countdown = (int) (0.5 * App.FPS);

    /**
     * Creates an explosion in the game grid.
     *
     * @param app    reference to application
     * @param sprite bomb sprite to render
     * @param gridX  x position in grid
     * @param gridY  y position in grid
     */
    public Explosion(App app, PImage sprite, int gridX, int gridY) {
        super(app, sprite, gridX, gridY);
    }

    /**
     * Disappears after countdown to 0.
     */
    @Override
    public void update() {
        countdown--;
        if (countdown == 0) {
            destroy();
        }
    }

    /**
     * Breaks breakable object that collides with this explosion.
     *
     * @param other the other object in collision
     */
    @Override
    public void onCollide(GameObject other) {
        if (other.isBreakable()) {
            other.destroy();
        }
    }
}
