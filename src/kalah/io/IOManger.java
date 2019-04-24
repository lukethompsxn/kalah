package kalah.io;

import kalah.action.Action;
import kalah.util.GameBoard;

public interface IOManger {
    void render(GameBoard gameBoard);
    Action requestPlayerAction(GameBoard gameBoard);

}
