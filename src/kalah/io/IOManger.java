package kalah.io;

import kalah.action.Action;
import kalah.util.GameBoard;

public interface IOManger {
    void render(GameBoard gameBoard);
    void renderTermination();
    void renderError(String errorMessage);
    Action requestPlayerAction(GameBoard gameBoard);

}
