package kalah.util;

import kalah.exception.InvalidActionException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KalahBoard implements GameBoard {
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private PitCollection pits;

    private boolean isActive;
    private boolean gameCompleted;

    public KalahBoard(Player p1, Player p2, PitCollection pits) {
        this.p1 = p1;
        this.p2 = p2;
        this.pits = pits;

        this.currentPlayer = p1;
        this.isActive = true;
        this.gameCompleted = false;
    }

    @Override
    public Player getP1() {
        return p1;
    }

    @Override
    public Player getP2() {
        return p2;
    }

    @Override
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public Player getOpponentPlayer() {
        if (currentPlayer.equals(p1)) {
            return p2;
        } else {
            return p1;
        }
    }

    @Override
    public PitCollection getPits() {
        return pits;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void terminate() {
        isActive = false;
    }

    @Override
    public void executeMove(int position) throws InvalidActionException {
        Pit house = pits.getPlayerHouse(currentPlayer, position);
        int seedCount = house.getSeeds();

        if (seedCount == 0) {
            throw new InvalidActionException("House is empty. Move again.");
        }

        house.clearSeeds();

        Pit pit = null;
        pits.configureIterator(currentPlayer, getOpponentPlayer(), house);
        for (int i = 0; i < seedCount; i++) {
            pit = nextPit();
            pit.addSeed();
        }

        if (pit == null || isPlayersOwnStore(pit)) {
            // do nothing
        } else if (isOpponentsTurn(pit)) {
            switchPlayer();
        } else if (isCapture(pit)) {
            doCapture(pit);
        } else {
            switchPlayer();
        }
    }

    private void switchPlayer() {
        if (currentPlayer.equals(p1)) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
    }

    @Override
    public boolean canContinue() {
        Iterator<Pit> playerPits = pits.getPlayerHouses(currentPlayer);

        while (playerPits.hasNext()) {
            if (playerPits.next().getSeeds() > 0) {
                return true;
            }
        }

        gameCompleted = true;
        return false;
    }

    @Override
    public boolean isGameCompleted() {
        return gameCompleted;
    }

    @Override
    public Map<Player, Integer> getFinalScores() {
        Map<Player, Integer> scores = new HashMap<>();
        scores.put(getOpponentPlayer(), getTotalSeeds() - pits.getPlayerStore(currentPlayer).getSeeds());
        scores.put(currentPlayer, pits.getPlayerStore(currentPlayer).getSeeds());
        return scores;
    }

    private Pit nextPit() {
        Pit nextPit = pits.getNextPit();

        while (nextPit.equals(pits.getPlayerStore(getOpponentPlayer()))) {
            nextPit = pits.getNextPit();
        }

        return nextPit;
    }

    // Case 1
    private boolean isOpponentsTurn(Pit pit) {
        return pits.isPlayersHouse(getOpponentPlayer(), pit)
                || pit.getSeeds() > 1;
    }

    // Case 2
    private boolean isPlayersOwnStore(Pit pit) {
        return pit.equals(pits.getPlayerStore(currentPlayer));
    }

    // Case 3
    private boolean isCapture(Pit pit) {
        return pit.getSeeds() == 1
                && pits.isPlayersHouse(currentPlayer, pit)
                && pits.getOppositePit(getOpponentPlayer(), pit).getSeeds() > 0;
    }

    private void doCapture(Pit pit) {
        Pit oppositePit = pits.getOppositePit(getOpponentPlayer(), pit);
        pit.clearSeeds();

        // + 1 to account for the seed which caused the capture
        pits.getPlayerStore(currentPlayer).addSeeds(oppositePit.getSeeds() + 1);
        oppositePit.clearSeeds();

        switchPlayer();
    }

    private int getTotalSeeds() {
        return Constants.NUM_PLAYERS * Constants.NUM_PITS * Constants.NUM_SEEDS;
    }
}
