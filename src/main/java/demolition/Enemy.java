package demolition;

import java.util.List;
import processing.core.PImage;

/**
 * Enemy moves in a certain pattern and causes player to lose life on collision.
 * There are two types of enemy with different moving behaviours. Use factory
 * methods to create Enemy object.
 */
public abstract class Enemy extends AnimatedGameObject {

    /**
     * The number of game frames between two consecutive enemy movements.
     */
    private static final int moveInterval = App.FPS;

    /**
     * The number of game frames since the enemy last moves.
     */
    private int moveFrameCount;

    private Enemy(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        super(app, sprites, gridX, gridY);
    }

    /**
     * Enemy is breakable.
     *
     * @return <code>true</code>
     */
    @Override
    public boolean isBreakable() {
        return true;
    }

    /**
     * Move in time intervals. If the current direction is blocked.
     */
    @Override
    public void update() {
        if (moveFrameCount == moveInterval) {
            while (movementBlocked()) {
                turn(chooseDirection());
            }
            move();
            moveFrameCount = 0;
        }

        moveFrameCount++;
    }

    /**
     * Override this method to define how the enemy chooses its next direction if
     * its movement is blocked.
     *
     * @return the direction to turn to when movement is blocked
     */
    protected abstract Direction chooseDirection();

    /**
     * Players loses one life if collides with enemy.
     *
     * @param other the other object in the collision
     */
    @Override
    protected void onCollide(GameObject other) {
        if (other instanceof Player) {
            app.loseOneLife();
        }
    }

    /**
     * Creates a red enemy. A red enemy chooses direction clockwise.
     *
     * @param app   reference to application
     * @param gridX x position in grid
     * @param gridY y position in grid
     * @return a red enemy
     */
    public static Enemy redEnemy(App app, int gridX, int gridY) {
        return new Enemy(app, app.getLoader().getAnimatedSprite("red_enemy"), gridX, gridY) {
            protected Direction chooseDirection() {
                return Direction.next(getDirection());
            }
        };
    }

    /**
     * Creates a yello enemy. A yellow enemy chooses direction randomly.
     *
     * @param app   reference to application
     * @param gridX x position in grid
     * @param gridY y position in grid
     * @return a yello enemy
     */
    public static Enemy yellowEnemy(App app, int gridX, int gridY) {
        return new Enemy(app, app.getLoader().getAnimatedSprite("yellow_enemy"), gridX, gridY) {
            protected Direction chooseDirection() {
                return Direction.random();
            }
        };
    }
}
