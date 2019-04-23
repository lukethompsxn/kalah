package kalah.engine;

import kalah.display.GameVisualiser;
import kalah.util.*;

import java.util.HashMap;
import java.util.Map;

public class GameEngine {
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;

    private GameBoard gameBoard;
    private GameVisualiser visualiser;

    private int numPits;
    private int numSeeds;

    public GameEngine(GameVisualiser visualiser, int numPits, int numSeeds) {
        this.visualiser = visualiser;
        this.numPits = numPits;
        this.numSeeds = numSeeds;
    }

    public void initialise() {

        // Initialise Houses
        Map<Integer, Pit> p1Houses = new HashMap<>();
        Map<Integer, Pit> p2Houses = new HashMap<>();

        for (int index = 1; index <= numPits; index++) {
            p1Houses.put(index, new House(numPits));
            p2Houses.put(index, new House(numPits));
        }

        // Initialise Players
        Player p1 = new Player(PLAYER1, new Store(numSeeds), p1Houses);
        Player p2 = new Player(PLAYER2, new Store(numSeeds), p2Houses);

        // Initialise Board
        gameBoard = new GameBoard(p1, p2);
    }

    public void play() {
        visualiser.render(gameBoard);
    }

}
