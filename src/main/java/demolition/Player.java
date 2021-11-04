package demolition;

/**
 * User control instance of the this class to move and place bombs.
 */
public class Player extends AnimatedGameObject {

    /**
     * Creates a controllable player object in the player layer of the game grid.
     *
     * @param app   reference to application
     * @param gridX x position in grid
     * @param gridY y position in grid
     */
    public Player(App app, int gridX, int gridY) {
        super(app, app.getLoader().getAnimatedSprite("player"), gridX, gridY, Layer.PLAYER);
    }

    /**
     * Player is breakable.
     *
     * @return <code>true</code>
     */
    @Override
    public boolean isBreakable() {
        return true;
    }

    /**
     * The player loses one life when destroyed.
     */
    @Override
    public void destroy() {
        app.loseOneLife();
    }

    /**
     * Controls the player to move in the given direction. Will not turn if the
     * movement is blocked.
     *
     * @param newDirection the direction to move towards
     */
    public void control(Direction newDirection) {
        if (!movementBlocked(newDirection)) {
            turn(newDirection);
            move();
        }
    }

    /**
     * Controls the player to place a bomb in the game grid.
     */
    public void placeBomb() {
        for (GameObject gameObject : app.getManager().atPos(gridX, gridY)) {
            if (gameObject instanceof Bomb) {
                return;
            }
        }
        new Bomb(app, gridX, gridY);
    }
}
