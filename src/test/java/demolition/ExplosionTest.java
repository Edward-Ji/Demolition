package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExplosionTest {

    // Test that explosion objects destroys itself after a certain length of time.
    @Test
    public void destoryTest() {
        App testApp = AppTest.testApp("src/test/config/config_complex_level.json");

        Explosion.explosionCentre(testApp, 1, 1, 0);
        for (int i = 0; i < 0.5 * App.FPS + 3; i++) {
            testApp.draw();
        }

        assertTrue(testApp.getManager().atPos(1, 1).isEmpty());
    }

    // Test that explosion do not destory non-destructable object.
    @Test
    public void onCollideTest() {
        App testApp = AppTest.testApp("src/test/config/config_complex_level.json");

        Explosion.explosionCentre(testApp, 5, 7, 0);
        for (int i = 0; i < 0.5 * App.FPS + 3; i++) {
            testApp.draw();
        }

        assertFalse(testApp.getManager().atPos(1, 1).isEmpty());
    }
}
