package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

    // Test the existance and validity four objects of direction enum.
    @Test
    public void constructorTest() {
        Direction up = Direction.UP;
        Direction down = Direction.DOWN;
        Direction left = Direction.LEFT;
        Direction right = Direction.RIGHT;

        assertNotNull(up);
        assertNotNull(down);
        assertNotNull(left);
        assertNotNull(right);
    }

    // Test the helper getter methods of direction enums.
    @Test
    public void getterTest() {
        Direction up = Direction.UP;

        assertEquals(0, up.getDeltaX());
        assertEquals(-1, up.getDeltaY());
        assertEquals(3, up.getAlphabetOrder());
    }

    // Test the next can correctly calculate the next direction clockwise.
    @Test
    public void nextDirectionTest() {
        assertEquals(Direction.LEFT, Direction.next(Direction.DOWN));
        assertEquals(Direction.UP, Direction.next(Direction.LEFT));
        assertEquals(Direction.RIGHT, Direction.next(Direction.UP));
        assertEquals(Direction.DOWN, Direction.next(Direction.RIGHT));
    }
}
