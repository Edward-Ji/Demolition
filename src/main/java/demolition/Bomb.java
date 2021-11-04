package demolition;

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
        Explosion.explosionCentre(app, gridX, gridY, BLAST_DISTANCE);
    }

    /**
     * A bomb destroys itself after the countdown reaches 0.
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
