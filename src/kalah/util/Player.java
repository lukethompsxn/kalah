package kalah.util;

public class Player {
    private int id;
    private Direction direction;

    public Player(int id, Direction direction) {
        this.id = id;
        this.direction = direction;
    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }
}
