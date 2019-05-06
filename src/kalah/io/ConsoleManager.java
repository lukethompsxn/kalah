package kalah.io;

import com.qualitascorpus.testsupport.IO;
import kalah.action.Action;
import kalah.action.GameOverAction;
import kalah.action.PlayerAction;
import kalah.util.*;

import java.util.Iterator;
import java.util.Map;

public class ConsoleManager implements IOManger {
    private static final int MINIMUM_DOUBLE_DIGITS = 10;
    private static final int BASE = 0;

    private static final String BOARD_BORDER_SIDES = "+----+";
    private static final String BOARD_HOUSE = "-------";
    private static final String BOARD_DIVIDER_SIDES = "|    |";
    private static final String BOARD_SPECIAL = "+";
    private static final String BOARD_PLAYER = "|%s%s%s|";
    private static final String BOARD_PIT_SINGLE = "| %d[ %d] ";
    private static final String BOARD_PIT_DOUBLE = "| %d[%d] ";
    private static final String BOARD_NAME = " P%s ";
    private static final String BOARD_STORE_SINGLE = "  %d ";
    private static final String BOARD_STORE_DOUBLE = " %d ";
    private static final String BOARD_VERTICAL_DIVIDER = "|";
    private static final String BOARD_INPUT = "Player P%d's turn - Specify house number or 'q' to quit: ";

    private IO io;
    private String border;
    private String divider;

    public ConsoleManager(IO io) {
        this.io = io;
        this.border = renderLine(BOARD_BORDER_SIDES);
        this.divider = renderLine(BOARD_DIVIDER_SIDES);
    }

    @Override
    public void renderBoard(GameBoard gameBoard) {
        Player p1 = gameBoard.getP1();
        Player p2 = gameBoard.getP2();
        PitCollection pits = gameBoard.getPits();

        io.println(border);
        io.println(String.format(BOARD_PLAYER, renderPlayerName(p2), renderPlayerHouses(pits, p2), renderPlayerStore(pits, p1)));
        io.println(divider);
        io.println(String.format(BOARD_PLAYER, renderPlayerStore(pits, p2), renderPlayerHouses(pits, p1), renderPlayerName(p1)));
        io.println(border);
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

    private String renderPlayerHouses(PitCollection pits, Player player) {
        StringBuilder output = new StringBuilder();
        Iterator<Pit> playerHouses = pits.getPlayerHouses(player);

        int index = 1;
        if (player.getDirection().equals(Direction.RIGHT)) {
            while (playerHouses.hasNext()) {
                output.append(getFormattedHouse(playerHouses.next(), index));
                index++;
            }
        } else {
            while (playerHouses.hasNext()) {
                output.insert(BASE, getFormattedHouse(playerHouses.next(), index));
                index++;
            }
        }

        output.append(BOARD_VERTICAL_DIVIDER);
        return output.toString();
    }

    private String renderPlayerName(Player player) {
        return String.format(BOARD_NAME, player.getId());
    }

    private String renderPlayerStore(PitCollection pits, Player player) {
        int seeds = pits.getPlayerStore(player).getSeeds();

        if (seeds < MINIMUM_DOUBLE_DIGITS) {
            return String.format(BOARD_STORE_SINGLE, seeds);
        } else {
            return String.format(BOARD_STORE_DOUBLE, seeds);
        }
    }

    private String renderLine(String sides) {
        StringBuilder line = new StringBuilder(sides);
        for (int i = 0; i < Constants.NUM_PITS; i++) {
            line.append(BOARD_HOUSE);
            if (i != Constants.NUM_PITS - 1) {
                line.append(BOARD_SPECIAL);
            }
        }
        line.append(sides);
        return line.toString();
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