package kalah.action;

import kalah.util.GameBoard;

public class GameOverAction implements Action {
    private GameBoard gameBoard;

    public GameOverAction(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void execute() {
        gameBoard.terminate();
    }
}
