package kalah.engine;

import kalah.io.IOManger;
import kalah.util.*;

import java.util.HashMap;
import java.util.Map;

public class KalahEngine implements GameEngine{
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;
    private static final int INITIAL_STORE_SEEDS = 0;

    private GameBoard gameBoard;
    private IOManger ioMananger;

    private int numPits;
    private int numSeeds;

    public KalahEngine(IOManger ioMananger, int numPits, int numSeeds) {
        this.ioMananger = ioMananger;
        this.numPits = numPits;
        this.numSeeds = numSeeds;
    }

    @Override
    public void initialise() {

        // Initialise Houses
        Map<Integer, Pit> p1Houses = new HashMap<>();
        Map<Integer, Pit> p2Houses = new HashMap<>();

        for (int index = 1; index <= numPits; index++) {
            p1Houses.put(index, new House(numSeeds));
            p2Houses.put(index, new House(numSeeds));
        }

        // Initialise Players
        Player p1 = new Player(PLAYER1, new Store(INITIAL_STORE_SEEDS), p1Houses, RenderDirection.FORWARDS);
        Player p2 = new Player(PLAYER2, new Store(INITIAL_STORE_SEEDS), p2Houses, RenderDirection.BACKWARDS);

        // Initialise Board
        gameBoard = new GameBoard(p1, p2);
    }

    @Override
    public void play() {
        while (gameBoard.isActive()) {
            ioMananger.render(gameBoard);
            ioMananger.requestPlayerAction(gameBoard).execute();
        }
    }
}
