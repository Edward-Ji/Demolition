package demolition;

import processing.core.PImage;

public class Wall extends GameObject {

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

}
