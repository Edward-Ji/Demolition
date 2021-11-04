package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WallTest {

    App testApp = AppTest.testApp();

    // Test the blocking movement attribtue. Both types of wall blocks movement.
    @Test
    public void blocksMovementTest() {
        Wall solidWall = Wall.solidWall(testApp, 1, 1);
        Wall brokenWall = Wall.brokenWall(testApp, 2, 2);

        assertTrue(solidWall.blocksMovement());
        assertTrue(brokenWall.blocksMovement());
    }

    // Test the breakable attribute. Solid wall can not be broken, while broken wall
    // can be destroyed by bombs.
    @Test
    public void isBreakableTest() {
        Wall solidWall = Wall.solidWall(testApp, 1, 1);
        Wall brokenWall = Wall.brokenWall(testApp, 2, 2);

        assertFalse(solidWall.isBreakable());
        assertTrue(brokenWall.isBreakable());
    }
}
