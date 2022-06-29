package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    // Test that player objects can be destroyed and can result in lost game.
    @Test
    public void destoryTest() {
        App testApp = AppTest.testApp();
        testApp.draw(); // Update game object list.

        assertTrue(testApp.player.isBreakable());

        Explosion.explosionCentre(testApp, 1, 1, 0);
        testApp.draw(); // Update game object list.
        testApp.draw(); // Update collision.

        assertEquals(App.Screen.LOST, testApp.screen);
    }

    // Player can not move to next position if there is an object that blocks
    // movement. Its direction remains unchaged in this case.
    @Test
    public void controlTest() {
        App testApp = AppTest.testApp();
        testApp.draw(); // Update game object list.

        testApp.player.control(Direction.LEFT);
        testApp.player.control(Direction.UP);

        assertEquals(1, testApp.player.getGridX());
        assertEquals(1, testApp.player.getGridY());
        assertEquals(Direction.DOWN, testApp.player.getDirection());
    }
}
