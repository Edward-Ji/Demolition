package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameObjectManagerTest {

    // Test that enemy objects can be destroyed by explosions.
    @Test
    public void inFieldTest() {
        GameObjectManager manager = new GameObjectManager();

        assertTrue(manager.inField(7, 6));
        assertFalse(manager.inField(7, -1));
        assertFalse(manager.inField(7, 13));
        assertFalse(manager.inField(-1, 6));
        assertFalse(manager.inField(15, 6));
        assertFalse(manager.inField(-1, -1));
        assertFalse(manager.inField(15, 13));
    }
}
