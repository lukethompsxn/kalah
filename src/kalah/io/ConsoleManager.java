package kalah.io;

import com.qualitascorpus.testsupport.IO;
import kalah.action.Action;
import kalah.action.GameAction;
import kalah.action.QuitAction;
import kalah.util.*;

import java.util.Map;

public class ConsoleManager implements IOManger {
    private static final int MINIMUM_DOUBLE_DIGITS = 10;
    private static final int BASE = 0;

    private static final String BOARD_BORDER = "+----+-------+-------+-------+-------+-------+-------+----+";
    private static final String BOARD_DIVIDER = "|    |-------+-------+-------+-------+-------+-------|    |";
    private static final String BOARD_PLAYER = "|%s%s%s|";
    private static final String BOARD_PIT = "| %d[ %d] ";
    private static final String BOARD_NAME = " P%s ";
    private static final String BOARD_STORE_SINGLE = "  %d ";
    private static final String BOARD_STORE_DOUBLE = " %d ";
    private static final String BOARD_VERTICAL_DIVIDER = "|";
    private static final String BOARD_INPUT = "Player %d's turn - Specify house number or 'q' to quit: ";

    private IO io;

    public ConsoleManager(IO io) {
        this.io = io;
    }

    @Override
    public void render(GameBoard gameBoard) {
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
        String response = io.readFromKeyboard(String.format(BOARD_INPUT, gameBoard.getCurrentPlayer().getId()));

        if (isValid(response)) {
            if (response.equals("q") || response.equals("Q")) {
                return new QuitAction(gameBoard);
            } else {
                try {
                    Player player = gameBoard.getCurrentPlayer();
                    Pit house = player.getHouses().get(Integer.parseInt(response));
                    return new GameAction(gameBoard, player, house);
                } catch (NumberFormatException e) {
                    // do nothing as falls through to return
                }
            }
        }
        return requestPlayerAction(gameBoard);
    }

    private String renderPlayerHouses(Player player) {
        StringBuilder output = new StringBuilder();
        Map<Integer, Pit> houses = player.getHouses();

        if (player.getRenderDirection().equals(RenderDirection.FORWARDS)) {
            for (Integer index : houses.keySet()) {
                output.append(String.format(BOARD_PIT, index, houses.get(index).getSeeds()));
            }
        } else {
            for (Integer index : houses.keySet()) {
                output.insert(BASE, String.format(BOARD_PIT, index, houses.get(index).getSeeds()));
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
}

