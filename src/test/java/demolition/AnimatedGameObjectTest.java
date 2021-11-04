package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimatedGameObjectTest {

    // Test animated objects can not move outside map.
    @Test
    public void moveUnboundedTest() {
        final String unboundedConfigPath = "src/test/config/config_unbounded_level.json";

        App testApp = AppTest.testApp(unboundedConfigPath);

        // Attempts to move player outside the grid.
        testApp.player.control(Direction.LEFT);
        testApp.player.control(Direction.UP);

        // Player shouldn't have moved.
        assertEquals(0, testApp.player.getGridX());
        assertEquals(0, testApp.player.getGridY());
        assertEquals(Direction.DOWN, testApp.player.getDirection());
    }
}
