package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Both solid and borken wall should blocks movement, but only broken wall is
 * breakable.
 */
public class WallTest {

    App testApp = AppTest.testApp();

    @Test
    public void blocksMovementTest() {
        Wall solidWall = Wall.solidWall(testApp, 1, 1);
        Wall brokenWall = Wall.brokenWall(testApp, 2, 2);

        assertTrue(solidWall.blocksMovement());
        assertTrue(brokenWall.blocksMovement());
    }

    @Test
    public void isBreakableTest() {
        Wall solidWall = Wall.solidWall(testApp, 1, 1);
        Wall brokenWall = Wall.brokenWall(testApp, 2, 2);

        assertFalse(solidWall.isBreakable());
        assertTrue(brokenWall.isBreakable());
    }
}
