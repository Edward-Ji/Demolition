package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoaderTest {

    // Path to multiple configuration files.
    private static final String configDirPath = "src/test/config/";
    private static final String complexConfigPath = configDirPath + "config_complex_level.json";
    private static final String invalidConfigPath = configDirPath + "config_invalid_level.json";
    private static final String multipleConfigPath = configDirPath + "config_multiple_level.json";

    // Test loading complex level.
    @Test
    public void loadLevelTest() {
        AppTest.testApp(complexConfigPath);
    }

    // Test loading a non-exist level file. The application switches to error
    // screen.
    @Test
    public void loadLevelInvalidTest() {
        App testApp = AppTest.testApp(invalidConfigPath);
        assertEquals(App.Screen.ERROR, testApp.screen);
    }

    // Test the loader can increment the level counter and load next level.
    @Test
    public void nextLevelTest() {
        App testApp = AppTest.testApp(multipleConfigPath);
        testApp.getLoader().nextLevel();
        assertEquals(App.Screen.GAME, testApp.screen);
        testApp.getLoader().nextLevel();
        // Last level reached, application switches to win screen.
        assertEquals(App.Screen.WIN, testApp.screen);
    }
}
