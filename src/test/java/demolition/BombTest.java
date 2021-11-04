package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BombTest {

    // Test that bomb objects desotyes itself after a certain length of time.
    @Test
    public void destoryTest() {
        App testApp = AppTest.testApp("src/test/config/config_complex_level.json");

        Bomb bomb = new Bomb(testApp, 10, 1);
        for (int i = 0; i < App.FPS; i++) {
            testApp.draw();
        }
        assertFalse(bomb.isDestroyed());

        for (int i = 0; i < App.FPS + 3; i++) {
            testApp.draw();
        }
        assertTrue(bomb.isDestroyed());
    }

    // Test that bomb can not turn.
    @Test
    public void turnTest() {
        App testApp = AppTest.testApp();

        Bomb bomb = new Bomb(testApp, 1, 1);
        bomb.turn(Direction.UP);

        // Direction remains default.
        assertEquals(Direction.DOWN, bomb.getDirection());
    }
}
