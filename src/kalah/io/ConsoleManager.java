package kalah.io;

import com.qualitascorpus.testsupport.IO;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
import kalah.action.Action;
import kalah.action.GameOverAction;
import kalah.action.PlayerAction;
import kalah.util.*;

import java.util.Iterator;
import java.util.Map;

public class ConsoleManager implements IOManger {
    private static final int MINIMUM_DOUBLE_DIGITS = 10;

    private static final String BOARD_BORDER = "+---------------+";
    private static final String BOARD_DIVIDER = "+-------+-------+";
    private static final String BOARD_PIT_SINGLE = " %d[ %d] ";
    private static final String BOARD_PIT_DOUBLE = " %d[%d] ";
    private static final String BOARD_ROW = "|%s|%s|";
    private static final String BOARD_PLAYER_SPACE = "       ";
    private static final String BOARD_STORE_SINGLE = " P%d  %d ";
    private static final String BOARD_STORE_DOUBLE = " P%d  %d";
    private static final String BOARD_INPUT = "Player P%d's turn - Specify house number or 'q' to quit: ";

    private IO io;

    public ConsoleManager(IO io) {
        this.io = io;
    }

    @Override
    public void renderBoard(GameBoard gameBoard) {
        Player p1 = gameBoard.getP1();
        Player p2 = gameBoard.getP2();
        PitCollection pits = gameBoard.getPits();

        io.println(BOARD_BORDER);
        io.println(renderPlayer(pits, p2));
        io.println(BOARD_DIVIDER);
        renderHouses(pits, p1, p2);
        io.println(BOARD_DIVIDER);
        io.println(renderPlayer(pits, p1));
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

    private void renderHouses(PitCollection pits, Player p1, Player p2) {
        int index = 1;
        while (index <= Constants.NUM_PITS) {
            int p2PitNum = Constants.NUM_PITS - index + 1;
            String left = getFormattedHouse(pits.getPlayerHouse(p1, index), index);
            String right = getFormattedHouse(pits.getPlayerHouse(p2, p2PitNum),p2PitNum);
            io.println(String.format(BOARD_ROW, left, right));
            index++;
        }
    }

    private String renderPlayer(PitCollection pits, Player player) {
        String left;
        String right;

        if (player.getDirection().equals(Direction.RIGHT)) {
            left = renderPlayerStore(pits, player);
            right = BOARD_PLAYER_SPACE;
        } else {
            left = BOARD_PLAYER_SPACE;
            right = renderPlayerStore(pits, player);
        }

        return String.format(BOARD_ROW, left, right);
    }

    private String renderPlayerStore(PitCollection pits, Player player) {
        int seeds = pits.getPlayerStore(player).getSeeds();

        if (seeds < MINIMUM_DOUBLE_DIGITS) {
            return String.format(BOARD_STORE_SINGLE, player.getId(), seeds);
        } else {
            return String.format(BOARD_STORE_DOUBLE, player.getId(), seeds);
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

    private String getFormattedHouse(Pit house, Integer index) {
        int seeds = house.getSeeds();
        String fmt = seeds < MINIMUM_DOUBLE_DIGITS ? BOARD_PIT_SINGLE : BOARD_PIT_DOUBLE;
        return String.format(fmt, index, seeds);
    }
}