package demolition;

import java.util.List;
import processing.core.PImage;

public class Player extends AnimatedGameObject {

    static final int layer = 0;

    public Player(App app, List<List<PImage>> sprites, int gridX, int gridY) {
        super(app, sprites, gridX, gridY, Layer.FOREGROUND);
    }

    @Override
    public void update() {

    }

}
