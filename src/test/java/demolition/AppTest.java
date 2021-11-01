package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void simpleTest() {
        assertEquals(480, App.HEIGHT);
        assertEquals(480, App.WIDTH);
    }
}
