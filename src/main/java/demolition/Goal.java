package demolition;

import processing.core.PImage;

public class Goal extends GameObject {

    public Goal(App app, PImage sprite, int gridX, int gridY) {
        super(app, sprite, gridX, gridY, Layer.BACKGROUND);
    }

    @Override
    public void update() {
    }

    @Override
    public void onCollideTrigger(GameObject other) {
        if (other == app.player) {
            app.nextLevel();
        }
    }
}
