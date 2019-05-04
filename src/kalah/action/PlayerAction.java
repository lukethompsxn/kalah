package kalah.action;

import kalah.exception.InvalidActionException;
import kalah.util.GameBoard;

public class PlayerAction implements Action {
    private GameBoard gameBoard;
    private int position;

    public PlayerAction(GameBoard gameBoard, int position) {
        this.gameBoard = gameBoard;
        this.position = position;
    }

    @Override
    public void execute() throws InvalidActionException {
        gameBoard.executeMove(position);
    }
}
