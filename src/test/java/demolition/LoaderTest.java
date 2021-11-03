package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoaderTest {

    private static final String configDirPath = "src/test/config/";
    private static final String complexConfigPath = configDirPath + "config_complex_level.json";
    private static final String invalidConfigPath = configDirPath + "config_invalid_level.json";
    private static final String multipleConfigPath = configDirPath + "config_multiple_level.json";

    @Test
    public void loadLevelTest() {
        AppTest.testApp(complexConfigPath);
    }

    @Test
    public void loadLevelInvalidTest() {
        App testApp = AppTest.testApp(invalidConfigPath);
        assertEquals(App.Screen.ERROR, testApp.screen);
    }

    @Test
    public void nextLevelTest() {
        App testApp = AppTest.testApp(multipleConfigPath);
        testApp.getLoader().nextLevel();
        assertEquals(App.Screen.GAME, testApp.screen);
        testApp.getLoader().nextLevel();
        assertEquals(App.Screen.WIN, testApp.screen);
    }
}