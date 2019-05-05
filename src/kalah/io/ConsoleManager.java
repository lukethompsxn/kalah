package kalah.io;

import com.qualitascorpus.testsupport.IO;
import kalah.action.Action;
import kalah.action.GameOverAction;
import kalah.action.PlayerAction;
import kalah.util.*;

import java.util.Map;

public class ConsoleManager implements IOManger {
    private static final int MINIMUM_DOUBLE_DIGITS = 10;
    private static final int BASE = 0;

    private static final String BOARD_BORDER = "+----+-------+-------+-------+-------+-------+-------+----+";
    private static final String BOARD_DIVIDER = "|    |-------+-------+-------+-------+-------+-------|    |";
    private static final String BOARD_PLAYER = "|%s%s%s|";
    private static final String BOARD_PIT_SINGLE = "| %d[ %d] ";
    private static final String BOARD_PIT_DOUBLE = "| %d[%d] ";
    private static final String BOARD_NAME = " P%s ";
    private static final String BOARD_STORE_SINGLE = "  %d ";
    private static final String BOARD_STORE_DOUBLE = " %d ";
    private static final String BOARD_VERTICAL_DIVIDER = "|";
    private static final String BOARD_INPUT = "Player P%d's turn - Specify house number or 'q' to quit: ";

    private IO io;

    public ConsoleManager(IO io) {
        this.io = io;
    }

    @Override
    public void renderBoard(GameBoard gameBoard) {
        Player p1 = gameBoard.getP1();
        Player p2 = gameBoard.getP2();

        io.println(BOARD_BORDER);
        io.println(String.format(BOARD_PLAYER, renderPlayerName(p2), renderPlayerHouses(p2), renderPlayerStore(p1)));
        io.println(BOARD_DIVIDER);
        io.println(String.format(BOARD_PLAYER, renderPlayerStore(p2), renderPlayerHouses(p1),  renderPlayerName(p1)));
        io.println(BOARD_BORDER);
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

    private String renderPlayerHouses(Player player) {
        StringBuilder output = new StringBuilder();
        Map<Integer, Pit> houses = player.getHouses();

        if (player.getRenderDirection().equals(RenderDirection.FORWARDS)) {
            for (Integer index : houses.keySet()) {
                output.append(getFormattedHouse(houses, index));
            }
        } else {
            for (Integer index : houses.keySet()) {
                output.insert(BASE, getFormattedHouse(houses, index));
            }
        }

        output.append(BOARD_VERTICAL_DIVIDER);
        return output.toString();
    }

    private String renderPlayerName(Player player) {
        return String.format(BOARD_NAME, player.getId());
    }

    private String renderPlayerStore(Player player) {
        int seeds = player.getStore().getSeeds();

        if (seeds < MINIMUM_DOUBLE_DIGITS) {
            return String.format(BOARD_STORE_SINGLE, seeds);
        } else {
            return String.format(BOARD_STORE_DOUBLE, seeds);
        }
    }

    private boolean isValid(String response) {
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

    private String getFormattedHouse(Map<Integer, Pit> houses, Integer index) {
        int seeds = houses.get(index).getSeeds();
        String fmt = seeds < MINIMUM_DOUBLE_DIGITS ? BOARD_PIT_SINGLE : BOARD_PIT_DOUBLE;
        return String.format(fmt, index, seeds);
    }
}