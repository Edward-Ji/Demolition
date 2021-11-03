package demolition;

public class Goal extends GameObject {

    public Goal(App app, int gridX, int gridY) {
        super(app, app.getLoader().getStaticSprite("goal"), gridX, gridY, Layer.BACKGROUND);
    }

    @Override
    public void update() {
    }

    @Override
    public void onCollide(GameObject other) {
        if (other == app.player) {
            app.getLoader().nextLevel();
        }
    }
}
