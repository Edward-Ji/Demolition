package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import processing.core.PApplet;

public class AppTest {

    private static final String testConfigPath = "src/test/config/config_empty_level.json";

    /*
     * Helper method that creates a sketch app for testing
     */
    public static App testApp() {
        return testApp(testConfigPath);
    }

    @SuppressWarnings("deprecation")
    public static App testApp(String configPath) {
        App app = new App(configPath);
        app.noLoop();
        PApplet.runSketch(new String[] { "TestApp" }, app);
        app.setup();
        app.delay(1000);

        GameObject.clearAll();

        return app;
    }

    @Test
    public void getLoaderTest() {
        App testApp = testApp();
        assertNotNull(testApp.getLoader());
    }

    @Test
    public void screenTest() {
        App testApp = testApp();
        testApp.screen = App.Screen.WIN;
        testApp.draw();
        testApp.screen = App.Screen.LOST;
        testApp.draw();
    }

    @Test
    public void loseByLivesTest() {
        App testApp = testApp();
        assertEquals(App.Screen.GAME, testApp.screen);
        testApp.loseOneLife();
        testApp.draw();
        assertEquals(App.Screen.LOST, testApp.screen);
    }

    @Test
    public void loseByTimerTest() {
        App testApp = testApp();
        assertEquals(App.Screen.GAME, testApp.screen);
        boolean lost = false;
        // Simulate running the app for one second.
        for (int i = 0; i < App.FPS + 1; i++) {
            testApp.draw();
            if (testApp.screen == App.Screen.LOST) {
                lost = true;
                break;
            }
        }
        assert lost;
    }

    @Test
    public void keyPressedTest() {
        App testApp = testApp();

        // Test arrow key to player movement.
        final int[] arrowKeyCodes = { 0, PApplet.RIGHT, PApplet.DOWN, PApplet.LEFT, PApplet.UP };
        final int[] expectedGridX = { 1, 2, 2, 1, 1 };
        final int[] expectedGridY = { 1, 1, 2, 2, 1 };
        for (int i = 0; i < arrowKeyCodes.length; i++) {
            testApp.key = PApplet.CODED;
            testApp.keyCode = arrowKeyCodes[i];
            testApp.keyPressed();
            assertEquals(expectedGridX[i], testApp.player.getGridX());
            assertEquals(expectedGridY[i], testApp.player.getGridY());
        }

        // Test space key to bomb placement.
        testApp.key = ' ';
        testApp.keyCode = ' ';
        testApp.keyPressed();
        testApp.key = PApplet.CODED;
        testApp.keyCode = PApplet.RIGHT;
        testApp.keyPressed();

        GameObject.updateAll();
        assertFalse(GameObject.atPos(1, 1).isEmpty());

        // Test press undefined key does nothing.
        testApp.key = 'x';
        testApp.keyCode = 'x';
        testApp.keyPressed();
    }
}
