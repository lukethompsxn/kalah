package kalah.util;

public class Store implements Pit {
    private int seeds;

    public Store(int seeds) {
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

    @Override
    public int getId() {
        return 0;
    }

    public void addSeeds(int numSeeds) {
        seeds += numSeeds;
    }
}
