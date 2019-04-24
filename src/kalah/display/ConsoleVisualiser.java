package kalah.display;

import com.qualitascorpus.testsupport.IO;
import kalah.util.GameBoard;
import kalah.util.Pit;
import kalah.util.Player;
import kalah.util.RenderDirection;

import java.util.Map;

public class ConsoleVisualiser implements GameVisualiser {
    private static final int MINIMUM_DOUBLE_DIGITS = 10;
    private static final int BASE = 0;

    private IO io;

    public ConsoleVisualiser(IO io) {
        this.io = io;
    }

    @Override
    public void render(GameBoard gameBoard) {
        Player p1 = gameBoard.getP1();
        Player p2 = gameBoard.getP2();

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println(String.format("|%s%s%s|", renderPlayerName(p2), renderPlayerHouses(p2), renderPlayerStore(p1)));
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println(String.format("|%s%s%s|", renderPlayerStore(p2), renderPlayerHouses(p1),  renderPlayerName(p1)));
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println(String.format("Player %d's turn - Specify house number or 'q' to quit: ", gameBoard.getCurrentPlayer().getId()));
    }

    private String renderPlayerHouses(Player player) {
        StringBuilder output = new StringBuilder();
        Map<Integer, Pit> houses = player.getHouses();

        if (player.getRenderDirection().equals(RenderDirection.FORWARDS)) {
            for (Integer index : houses.keySet()) {
                output.append(String.format("| %d[ %d] ", index, houses.get(index).getSeeds()));
            }
        } else {
            for (Integer index : houses.keySet()) {
                output.insert(BASE, String.format("| %d[ %d] ", index, houses.get(index).getSeeds()));
            }
        }

        output.append("|");
        return output.toString();
    }

    private String renderPlayerName(Player player) {
        return String.format(" P%s ", player.getId());
    }

    private String renderPlayerStore(Player player) {
        int seeds = player.getStore().getSeeds();

        if (seeds < MINIMUM_DOUBLE_DIGITS) {
            return String.format("  %d ", seeds);
        } else {
            return String.format(" %d ", seeds);
        }
    }
}

