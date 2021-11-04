package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GoalTest {

    private static final String configPath = "src/test/config/config_goal_level.json";

    // Test that goal collides with player causes wining this level.
    @Test
    public void onCollideTest() {
        App testApp = AppTest.testApp(configPath);

        testApp.draw(); // Update game objects.

        // Collide with enemy game continues.
        Enemy enemy = (Enemy) testApp.getManager().atPos(2, 1).get(0);
        enemy.move();
        testApp.draw(); // Handles gamme objects collision.
        testApp.draw(); // Update app screen.
        assertEquals(App.Screen.GAME, testApp.screen);

        // Control player to collide with the goal.
        testApp.player.control(Direction.DOWN);
        testApp.draw(); // Update game objects.
        testApp.draw(); // Handles gamme objects collision.
        testApp.draw(); // Update app screen.

        // The configuration only has one level.
        // Winnning the only level is winning the game.
        assertEquals(App.Screen.WIN, testApp.screen);
    }
}
