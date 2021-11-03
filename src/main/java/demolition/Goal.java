package demolition;

/**
 * The goal in current level.
 */
public class Goal extends GameObject {

    /**
     * Creates a goal in the game grid background layer.
     *
     * @param app   reference to application
     * @param gridX x position in grid
     * @param gridY y position in grid
     */
    public Goal(App app, int gridX, int gridY) {
        super(app, app.getLoader().getStaticSprite("goal"), gridX, gridY, Layer.BACKGROUND);
    }

    /**
     * The player goes to the next level when collides with the goal.
     *
     * @param other the other object in the collision
     */
    @Override
    public void onCollide(GameObject other) {
        if (other == app.player) {
            app.getLoader().nextLevel();
        }
    }
}
