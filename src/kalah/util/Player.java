package kalah.util;

import java.util.Map;

public class Player {
    private int id;
    private Pit store;
    private Map<Integer, Pit> houses;

    public Player(int id, Pit store, Map<Integer, Pit> houses) {
        this.id = id;
        this.store = store;
        this.houses = houses;
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
}
