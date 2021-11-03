package demolition;

public class Player extends AnimatedGameObject {

    static final int layer = 0;

    public Player(App app, int gridX, int gridY) {
        super(app, app.getLoader().getAnimatedSprite("player"), gridX, gridY, Layer.FOREGROUND);
    }

    @Override
    public boolean isBreakable() {
        return true;
    }

    @Override
    public void destroy() {
        app.loseOneLife();
    }

    public void control(Direction newDirection) {
        if (!movementBlocked(newDirection)) {
            turn(newDirection);
            move();
        }
    }

    public void placeBomb() {
        for (GameObject gameObject : GameObject.atPos(gridX, gridY)) {
            if (gameObject instanceof Bomb) {
                return;
            }
        }
        new Bomb(app, gridX, gridY);
    }

}
