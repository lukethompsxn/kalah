package kalah.display;

import com.qualitascorpus.testsupport.IO;
import kalah.util.GameBoard;
import kalah.util.Pit;
import kalah.util.Player;

import java.util.Map;

public class ConsoleVisualiser implements GameVisualiser {
    private static final String NEW_LINE = "\n";

    private IO io;

    public ConsoleVisualiser(IO io) {
        this.io = io;
    }

    @Override
    public void render(GameBoard gameBoard) {
        StringBuilder output = new StringBuilder();
        output.append("+----+-------+-------+-------+-------+-------+-------+----+");
        output.append(NEW_LINE);

        renderPlayer(output, gameBoard.getP2());
        output.append(NEW_LINE);

        output.append("|    |-------+-------+-------+-------+-------+-------|    |");
        output.append(NEW_LINE);

        renderPlayer(output, gameBoard.getP1());
        output.append(NEW_LINE);

        output.append("+----+-------+-------+-------+-------+-------+-------+----+");
        output.append(NEW_LINE);

        output.append(String.format("Player %d's turn - Specify house number or 'q' to quit: ", gameBoard.getCurrentPlayer().getId()));

        io.println(output.toString());
    }

    private void renderPlayer(StringBuilder output, Player player) {
        Pit store = player.getStore();
        Map<Integer, Pit> houses = player.getHouses();

        output.append(String.format("| P%s ", player.getId()));
        for (Integer index : houses.keySet()) {
            output.append(String.format("| %d[ %d] ", index, houses.get(index).getSeeds()));
        }
        output.append(String.format("| %d |", store.getSeeds()));
    }
}
