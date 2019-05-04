package kalah.util;

public class Pit {
    public enum PitType {HOUSE, STORE}

    private int id;
    private int seeds;
    private PitType pitType;

    public Pit(int id, int seeds, PitType pitType) {
        this.id = id;
        this.seeds = seeds;
        this.pitType = pitType;
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

    public PitType getPitType() {
        return pitType;
    }
}


