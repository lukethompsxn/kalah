package kalah.io;

import com.qualitascorpus.testsupport.IO;
import kalah.action.Action;
import kalah.action.GameOverAction;
import kalah.action.PlayerAction;
import kalah.io.board.BoardRenderer;
import kalah.util.GameBoard;
import kalah.util.Player;

import java.util.Map;

public class ConsoleManager implements IOManger {
    private static final String BOARD_INPUT = "Player P%d's turn - Specify house number or 'q' to quit: ";

    private IO io;
    private BoardRenderer boardRenderer;

    public ConsoleManager(IO io, BoardRenderer boardRenderer) {
        this.io = io;
        this.boardRenderer = boardRenderer;
    }

    @Override
    public void renderBoard(GameBoard gameBoard) {
        boardRenderer.renderBoard(gameBoard);
    }

    @Override
    public Action requestPlayerAction(GameBoard gameBoard) {
        if (!gameBoard.canContinue()) {
            return new GameOverAction(gameBoard);
        }

        String response = io.readFromKeyboard(String.format(BOARD_INPUT, gameBoard.getCurrentPlayer().getId()));

        if (isValid(response)) {
            if (response.equals("q") || response.equals("Q")) {
                return new GameOverAction(gameBoard);
            } else {
                try {
                    return new PlayerAction(gameBoard, Integer.parseInt(response));
                } catch (NumberFormatException e) {
                    // do nothing as it will fall through to return
                }
            }
        }
        return requestPlayerAction(gameBoard);
    }

    @Override
    public void renderTermination() {
        io.println("Game over");
    }

    @Override
    public void renderError(String errorMessage) {
        io.println(errorMessage);
    }

    @Override
    public void renderScores(GameBoard gameBoard) {
        Map<Player, Integer> scores = gameBoard.getFinalScores();
        io.println(String.format("\tplayer %d:%d", gameBoard.getP1().getId(), scores.get(gameBoard.getP1())));
        io.println(String.format("\tplayer %d:%d", gameBoard.getP2().getId(), scores.get(gameBoard.getP2())));

        if (scores.get(gameBoard.getP1()) > scores.get(gameBoard.getP2())) {
            io.println(String.format("Player %d wins!", gameBoard.getP1().getId()));
        } else if (scores.get(gameBoard.getP1()) < scores.get(gameBoard.getP2())) {
            io.println(String.format("Player %d wins!", gameBoard.getP2().getId()));
        } else {
            io.println("A tie!");
        }
    }

    private boolean isValid(String response) {
        if (response == null || response.isEmpty()) {
            return false;
        }

        if (response.equals("q") || response.equals("Q")) {
            return true;
        } else {
            try {
                Integer.parseInt(response);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }
}