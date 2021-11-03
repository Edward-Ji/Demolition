package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimatedGameObjectTest {

    @Test
    public void getLoaderTest() {
        App app = new App();
        assertNull(app.getLoader());
    }
}
