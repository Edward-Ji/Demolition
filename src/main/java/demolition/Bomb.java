package demolition;

import processing.core.PImage;

/**
 * Bomb will detonate after counting down and creates an explosion.
 */
public class Bomb extends AnimatedGameObject {

    /**
     * The reach of the explosion after the bomb detonates.
     */
    private static final int BLAST_DISTANCE = 2;

    /**
     * The number of game frames before the bomb detonates.
     */
    private int countdown = 2 * App.FPS;

    /**
     * Creates a bomb in the game grid.
     *
     * @param app   reference to application
     * @param gridX x position in grid
     * @param gridY y position in grid
     */
    public Bomb(App app, int gridX, int gridY) {
        super(app, app.getLoader().getAnimatedSprite("bomb"), gridX, gridY, Layer.BACKGROUND);
        // Overwrite the animation rate for bomb
        setFrameInterval((int) (0.25 * App.FPS));
    }

    /**
     * {@inheritDoc} But also detonates.
     */
    @Override
    public void destroy() {
        super.destroy();
        detonate();
    }

    /**
     * Creates an explosion in all four directions. Non-breakable objects stop the
     * explosion before them in that direction. Breakable objects stop the explosion
     * at their position in that direction.
     */
    public void detonate() {
        new Explosion(app, app.getLoader().getStaticSprite("explosion_centre"), gridX, gridY);
        direction: for (Direction direction : Direction.values()) {
            PImage sprite;
            if (direction.getDeltaX() == 0) {
                sprite = app.getLoader().getStaticSprite("explosion_vertical");
            } else {
                sprite = app.getLoader().getStaticSprite("explosion_horizontal");
            }
            for (int dist = 1; dist <= BLAST_DISTANCE; dist++) {
                int newGridX = gridX + direction.getDeltaX() * dist;
                int newGridY = gridY + direction.getDeltaY() * dist;
                for (GameObject gameObject : GameObject.atPos(newGridX, newGridY)) {
                    if (!gameObject.isBreakable()) {
                        continue direction;
                    }
                }
                new Explosion(app, sprite, newGridX, newGridY);
                for (GameObject gameObject : GameObject.atPos(newGridX, newGridY)) {
                    if (gameObject.blocksMovement()) {
                        continue direction;
                    }
                }
            }
        }
    }

    /**
     * A bomb destorys itself after the countdown reaches 0.
     */
    @Override
    public void update() {
        countdown--;
        if (countdown == 0) {
            destroy();
        }
    }

    /**
     * A bomb can not turn.
     *
     * @param newDirection <i>not used</i>
     */
    @Override
    public void turn(Direction newDirection) {
        // Left empty intentionally
        // Bomb should not be able to turn
    }

}
