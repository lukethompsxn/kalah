package kalah.util;

import java.util.Map;

public class Player {
    private int id;
    private Pit store;
    private Map<Integer, Pit> houses;
    private RenderDirection renderDirection;
    private int offset;

    public Player(int id, Pit store, Map<Integer, Pit> houses, RenderDirection renderDirection, int offset) {
        this.id = id;
        this.store = store;
        this.houses = houses;
        this.renderDirection = renderDirection;
        this.offset = offset;
    }

    public int getId() {
        return id;
    }

    public Pit getStore() {
        return store;
    }

    public Map<Integer, Pit> getHouses() {
        return houses;
    }

    public RenderDirection getRenderDirection() {
        return renderDirection;
    }

    public int getOffset() {
        return offset;
    }
}
