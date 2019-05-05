package kalah.engine;

import kalah.exception.InvalidActionException;
import kalah.io.IOManger;
import kalah.util.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KalahEngine implements GameEngine {
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;
    private static final int INITIAL_STORE_SEEDS = 0;
    private static final int STORE_ID = 0;

    private GameBoard gameBoard;
    private IOManger ioManager;

    public KalahEngine(IOManger ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public void initialise() {

        // Initialise Houses
        Map<Integer, Pit> p1Houses = new HashMap<>();
        Map<Integer, Pit> p2Houses = new HashMap<>();

        for (int index = 1; index <= Constants.NUM_PITS; index++) {
            p1Houses.put(index, new Pit(index, Constants.NUM_SEEDS, Pit.PitType.HOUSE));
            p2Houses.put(index, new Pit(index, Constants.NUM_SEEDS, Pit.PitType.HOUSE));
        }

        // Initialise Players
        Player p1 = new Player(PLAYER1, new Pit(STORE_ID, INITIAL_STORE_SEEDS, Pit.PitType.STORE), p1Houses, RenderDirection.FORWARDS, 0);
        Player p2 = new Player(PLAYER2, new Pit(STORE_ID, INITIAL_STORE_SEEDS, Pit.PitType.STORE), p2Houses, RenderDirection.BACKWARDS, Constants.NUM_PITS + 1);

        // Initialise Board
        List<Pit> boardPits = new ArrayList<>();
        boardPits.addAll(p1Houses.values());
        boardPits.add(p1.getStore());
        boardPits.addAll(p2Houses.values());
        boardPits.add(p2.getStore());

        gameBoard = new KalahBoard(p1, p2, boardPits);
    }

    @Override
    public void play() {
        while (gameBoard.isActive()) {
            ioManager.renderBoard(gameBoard);
            try {
                ioManager.requestPlayerAction(gameBoard).execute();
            } catch (InvalidActionException e) {
                ioManager.renderError(e.getMessage());
            }
        }
        ioManager.renderTermination();
        ioManager.renderBoard(gameBoard);

        if (gameBoard.isGameCompleted()) {
            ioManager.renderScores(gameBoard);
        }
    }
}
