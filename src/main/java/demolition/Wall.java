package demolition;

import processing.core.PImage;

public abstract class Wall extends GameObject {

    public Wall(App app, PImage sprite, int gridX, int gridY) {
        super(app, sprite, gridX, gridY, Layer.BACKGROUND);
    }

    @Override
    public boolean blocksMovement() {
        return true;
    }

    @Override
    public void update() {
    }

    public static Wall brokenWall(App app, int gridX, int gridY) {
        return new Wall(app, app.getLoader().getStaticSprite("broken_wall"), gridX, gridY) {
            @Override
            public boolean isBreakable() {
                return true;
            }
        };
    }

    public static Wall solidWall(App app, int gridX, int gridY) {
        return new Wall(app, app.getLoader().getStaticSprite("solid_wall"), gridX, gridY) {
        };
    }

}
