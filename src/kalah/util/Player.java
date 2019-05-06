package kalah.util;

public class Player {
    private int id;
    private RenderDirection renderDirection;

    public Player(int id, RenderDirection renderDirection) {
        this.id = id;
        this.renderDirection = renderDirection;
    }

    public int getId() {
        return id;
    }

    public RenderDirection getRenderDirection() {
        return renderDirection;
    }
}
