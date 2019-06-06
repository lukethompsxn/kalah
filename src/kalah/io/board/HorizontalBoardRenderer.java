package kalah.io.board;

import com.qualitascorpus.testsupport.IO;
import kalah.util.*;

import java.util.Iterator;

public class HorizontalBoardRenderer implements BoardRenderer {
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

    private IO io;
    private String border;
    private String divider;

    public HorizontalBoardRenderer(IO io) {
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

    private String getFormattedHouse(Pit house, Integer index) {
        int seeds = house.getSeeds();
        String fmt = seeds < MINIMUM_DOUBLE_DIGITS ? BOARD_PIT_SINGLE : BOARD_PIT_DOUBLE;
        return String.format(fmt, index, seeds);
    }
}
