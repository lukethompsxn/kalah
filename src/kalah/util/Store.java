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
}
