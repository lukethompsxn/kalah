package kalah.util;

public class House implements Pit {
    private int id;
    private int seeds;

    public House(int id, int seeds) {
        this.id = id;
        this.seeds = seeds;
    }

    @Override
    public int getSeeds() {
        return seeds;
    }

    @Override
    public void addSeed() {
        seeds++;
    }

    public int getId() {
        return id;
    }

    public void clearSeeds() {
        this.seeds = 0;
    }
}
