package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    // Test that enemy objects can be destoryed by explosions.
    @Test
    public void isBreakableTest() {
        App testApp = AppTest.testApp();

        Enemy redEnemy = Enemy.redEnemy(testApp, 2, 2);
        Enemy yellowEnemy = Enemy.yellowEnemy(testApp, 3, 3);

        Explosion.explosionCentre(testApp, 2, 2, 0);
        Explosion.explosionCentre(testApp, 3, 3, 0);

        testApp.draw(); // Update game object list.
        testApp.draw(); // Update collision.

        assertTrue(redEnemy.isDestroyed());
        assertTrue(yellowEnemy.isDestroyed());
    }

    // Test that red and yello enemy move according to the rules.
    @Test
    public void movementTest() {
        App testApp = AppTest.testApp();

        Enemy redEnemy = Enemy.redEnemy(testApp, 7, 12);
        Enemy yellowEnemy = Enemy.yellowEnemy(testApp, 7, 12);

        for (int i = 0; i < App.FPS + 1; i++) {
            redEnemy.update();
            yellowEnemy.update();
        }

        assertFalse(7 == redEnemy.getGridX() && 12 == redEnemy.getGridY());
        assertEquals(6, yellowEnemy.getGridX());
        assertEquals(12, yellowEnemy.getGridY());
    }

    // Test that red and yello enemy choose direction according to the rules.
    @Test
    public void chooseDirectionTest() {
        App testApp = AppTest.testApp();

        Enemy redEnemy = Enemy.redEnemy(testApp, 7, 12);
        Enemy yellowEnemy = Enemy.yellowEnemy(testApp, 7, 12);

        assertNotNull(redEnemy.chooseDirection());
        assertEquals(Direction.LEFT, yellowEnemy.chooseDirection());
    }

    // Test that enemy collides with player causes player to lose one life.
    @Test
    public void collidePlayerTest() {
        App testApp = AppTest.testApp();

        Enemy.redEnemy(testApp, 1, 1);
        Enemy.redEnemy(testApp, 12, 12);

        testApp.draw(); // Update game objects.
        testApp.draw(); // Handles gamme objects collision.
        testApp.draw(); // Update app screen.

        assertEquals(App.Screen.LOST, testApp.screen);
    }
}
