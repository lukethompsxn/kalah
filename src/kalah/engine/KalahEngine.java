package kalah.engine;

import kalah.exception.InvalidActionException;
import kalah.io.IOManger;
import kalah.util.*;

public class KalahEngine implements GameEngine {
    private static final int PLAYER1 = 1;
    private static final int PLAYER2 = 2;

    private GameBoard gameBoard;
    private IOManger ioManager;

    public KalahEngine(IOManger ioManager) {
        this.ioManager = ioManager;
    }

    @Override
    public void initialise() {
        // Initialise Players
        Player p1 = new Player(PLAYER1, RenderDirection.FORWARDS);
        Player p2 = new Player(PLAYER2, RenderDirection.BACKWARDS);

        // Initialise Board
        gameBoard = new KalahBoard(p1, p2, new PitCollection(p1, p2));
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
