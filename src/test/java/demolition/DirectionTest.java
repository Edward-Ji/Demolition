package demolition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {

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

    @Test
    public void getterTest() {
        Direction up = Direction.UP;

        assertEquals(0, up.getDeltaX());
        assertEquals(-1, up.getDeltaY());
        assertEquals(3, up.getAlphabetOrder());
    }

    @Test
    public void nextDirectionTest() {
        assertEquals(Direction.LEFT, Direction.next(Direction.DOWN));
        assertEquals(Direction.UP, Direction.next(Direction.LEFT));
        assertEquals(Direction.RIGHT, Direction.next(Direction.UP));
        assertEquals(Direction.DOWN, Direction.next(Direction.RIGHT));
    }

    @Test
    public void randomDirectionTest() {
        assertNotEquals(Direction.UP, Direction.random(Direction.UP));
        assertNotEquals(Direction.DOWN, Direction.random(Direction.DOWN));
        assertNotEquals(Direction.LEFT, Direction.random(Direction.LEFT));
        assertNotEquals(Direction.RIGHT, Direction.random(Direction.RIGHT));
    }
}
