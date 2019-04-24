package kalah.action;

import kalah.util.GameBoard;

public class QuitAction implements Action {
    private GameBoard gameBoard;

    public QuitAction(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void execute() {
        gameBoard.terminate();
    }
}
