package kalah.io;

import kalah.action.Action;
import kalah.util.GameBoard;

public interface IOManger {
    void renderBoard(GameBoard gameBoard);

    void renderTermination();

    void renderError(String errorMessage);

    void renderScores(GameBoard gameBoard);

    Action requestPlayerAction(GameBoard gameBoard);
}
