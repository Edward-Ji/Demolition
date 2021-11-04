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

    /**
     * Creates an explosion in all four directions. Non-breakable objects stop the
     * explosion before them in that direction. Breakable objects stop the explosion
     * at their position in that direction.
     *
     * @param app           reference to application
     * @param gridX         x position of explosion centre in grid
     * @param gridY         y position of explosion centre in grid
     * @param blastDistance the radius of explosion
     */
    public static void explosionCentre(App app, int gridX, int gridY, int blastDistance) {
        new Explosion(app, app.getLoader().getStaticSprite("explosion_centre"), gridX, gridY);
        direction: for (Direction direction : Direction.values()) {
            PImage sprite;
            if (direction.getDeltaX() == 0) {
                sprite = app.getLoader().getStaticSprite("explosion_vertical");
            } else {
                sprite = app.getLoader().getStaticSprite("explosion_horizontal");
            }
            for (int dist = 1; dist <= blastDistance; dist++) {
                int newGridX = gridX + direction.getDeltaX() * dist;
                int newGridY = gridY + direction.getDeltaY() * dist;
                for (GameObject gameObject : app.getManager().atPos(newGridX, newGridY)) {
                    if (!gameObject.isBreakable()) {
                        continue direction;
                    }
                }
                new Explosion(app, sprite, newGridX, newGridY);
                for (GameObject gameObject : app.getManager().atPos(newGridX, newGridY)) {
                    if (gameObject.blocksMovement()) {
                        continue direction;
                    }
                }
            }
        }
    }
}
