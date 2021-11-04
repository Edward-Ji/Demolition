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

    // Player can place infinite number of bombs, but they are non-stackable.
    @Test
    public void placeBombTest() {
        App testApp = AppTest.testApp();
        testApp.draw(); // Update game object list.

        // Can place bomb freely
        testApp.player.placeBomb();
        testApp.draw();
        testApp.draw();
        testApp.draw();
        assertEquals(2, testApp.getManager().atPos(1, 1).size());

        // Can not stack bomb
        testApp.player.placeBomb();
        testApp.draw();
        testApp.draw();
        testApp.draw();
        assertEquals(2, testApp.getManager().atPos(1, 1).size());
    }
}
