package kalah.util;

public class Pit {
    private int id;
    private int seeds;

    public Pit(int id, int seeds) {
        this.id = id;
        this.seeds = seeds;
    }

    public int getSeeds() {
        return seeds;
    }

    public void addSeed() {
        seeds++;
    }

    public void addSeeds(int numSeeds) {
        seeds += numSeeds;
    }

    public int getId() {
        return id;
    }

    public void clearSeeds() {
        this.seeds = 0;
    }
}


