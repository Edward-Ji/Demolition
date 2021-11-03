package demolition;

import processing.core.PImage;

/**
 * Wall are background objects that blocks movements. There are two types of
 * walls, one is breakable, the other is not.
 */
public abstract class Wall extends GameObject {

    private Wall(App app, PImage sprite, int gridX, int gridY) {
        super(app, sprite, gridX, gridY, Layer.BACKGROUND);
    }

    /**
     * Wall blocks movement.
     *
     * @return <code>true</code>
     */
    @Override
    public boolean blocksMovement() {
        return true;
    }

    /**
     * Creates a broken wall that can be broken down.
     *
     * @param app   reference to application
     * @param gridX x position on grid
     * @param gridY y position on grid
     * @return a broken wall
     */
    public static Wall brokenWall(App app, int gridX, int gridY) {
        return new Wall(app, app.getLoader().getStaticSprite("broken_wall"), gridX, gridY) {
            @Override
            public boolean isBreakable() {
                return true;
            }
        };
    }

    /**
     * Creates a default solid wall that can not be broken down.
     *
     * @param app   reference to application
     * @param gridX x position on grid
     * @param gridY y position on grid
     * @return a solid wall
     */
    public static Wall solidWall(App app, int gridX, int gridY) {
        return new Wall(app, app.getLoader().getStaticSprite("solid_wall"), gridX, gridY) {
        };
    }

}
