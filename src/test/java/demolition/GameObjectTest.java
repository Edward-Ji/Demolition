package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import processing.core.PImage;

public class GameObjectTest {

    App app = new App();
    PImage sprite = new PImage();

    @Test
    public void constructorTest() {
        assertNotNull(new GameObject(app, sprite, 0, 0) {
        });
        assertNotNull(new GameObject(app, sprite, 0, 0, GameObject.Layer.PLAYER) {
        });
    }
}
