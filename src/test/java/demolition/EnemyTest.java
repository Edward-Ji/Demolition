package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    private static final String configPath = "src/test/config/config_enemy_level.json";

    // Test that enemy objects can be destoryed by explosions.
    @Test
    public void isBreakableTest() {
        App testApp = AppTest.testApp(configPath);
        testApp.draw(); // Update game object list.

        GameObject redEnemy = testApp.getManager().atPos(1, 1).get(0);
        GameObject yellowEnemy = testApp.getManager().atPos(3, 1).get(0);

        Explosion.explosionCentre(testApp, 1, 1, 0);
        Explosion.explosionCentre(testApp, 3, 1, 0);

        testApp.draw(); // Update game object list.
        testApp.draw(); // Update collision.

        assertTrue(redEnemy.isDestroyed());
        assertTrue(yellowEnemy.isDestroyed());
    }

    // Test that red and yellow enemy move according to the rules.
    @Test
    public void movementTest() {
        App testApp = AppTest.testApp(configPath);
        testApp.draw(); // Update game object list.

        // Stuck enemy.
        Enemy stuckRedEnemy = (Enemy) testApp.getManager().atPos(1, 1).get(0);
        Enemy stuckYellowEnemy = (Enemy) testApp.getManager().atPos(3, 1).get(0);
        // Looping enemy.
        Enemy loopRedEnemy = (Enemy) testApp.getManager().atPos(6, 2).get(0);
        Enemy loopYellowEnemy = (Enemy) testApp.getManager().atPos(9, 2).get(0);

        for (int i = 0; i < App.FPS + 3; i++) {
            testApp.draw();
        }

        assertEquals(Direction.DOWN, stuckRedEnemy.getDirection());
        assertEquals(Direction.DOWN, stuckYellowEnemy.getDirection());
        assertNotEquals(Direction.DOWN, loopRedEnemy.getDirection());
        assertEquals(Direction.LEFT, loopYellowEnemy.getDirection());
    }

    // Test that enemy collides with player causes player to lose one life.
    @Test
    public void collidePlayerTest() {
        App testApp = AppTest.testApp(configPath);
        testApp.draw(); // Update game object list.

        // The enemy is two seconds from the player
        for (int i = 0; i < 2 * App.FPS + 3; i++) {
            testApp.draw();
        }

        assertEquals(App.Screen.LOST, testApp.screen);
    }
}
