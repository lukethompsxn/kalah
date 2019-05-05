package kalah.util;

import kalah.exception.InvalidActionException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KalahBoard implements GameBoard {
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private boolean isActive;
    private boolean gameCompleted;
    private List<Pit> boardPits;
    private int boardIndex;

    public KalahBoard(Player p1, Player p2, List<Pit> boardPits) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        this.isActive = true;
        this.gameCompleted = false;
        this.boardPits = boardPits;
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
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void terminate() {
        isActive = false;
    }

    @Override
    public void executeMove(int position) throws InvalidActionException {
        Pit house = currentPlayer.getHouses().get(position);
        boardIndex = currentPlayer.getOffset() + house.getId();

        if (house.getSeeds() == 0) {
            throw new InvalidActionException("House is empty. Move again.");
        }

        int seedCount = house.getSeeds();
        house.clearSeeds();

        Pit pit = null;
        for (int i = 0; i < seedCount; i++) {
            pit = getNextPit();
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
        for (Pit house : currentPlayer.getHouses().values()) {
            if (house.getSeeds() > 0) {
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
        scores.put(getOpponentPlayer(), getTotalSeeds() - currentPlayer.getStore().getSeeds());
        scores.put(currentPlayer, currentPlayer.getStore().getSeeds());
        return scores;
    }

    private Pit getNextPit() {
        if (isOpponentsStore(boardIndex, boardPits)) {
            boardIndex++;
        }

        if (boardIndex >= boardPits.size()) {
            boardIndex = 0;
        }

        boardIndex++;
        return boardPits.get(boardIndex - 1);
    }

    private boolean isOpponentsStore(int index, List<Pit> boardPits) {
        return index < boardPits.size()
                && boardPits.get(index) != null
                && boardPits.get(index).equals(getOpponentPlayer().getStore());
    }

    private Pit getOppositePit(Pit currentPit) {
        int numPits = p1.getHouses().size();
        return getOpponentPlayer().getHouses().get(numPits + 1 - currentPit.getId());
    }

    // Case 1
    private boolean isOpponentsTurn(Pit pit) {
        return !currentPlayer.getHouses().containsValue(pit)
                || pit.getSeeds() > 1;
    }

    // Case 2
    private boolean isPlayersOwnStore(Pit pit) {
        return pit.equals(currentPlayer.getStore());
    }

    // Case 3
    private boolean isCapture(Pit pit) {
        return currentPlayer.getHouses().containsValue(pit)
                && pit.getSeeds() == 1
                && pit.getPitType().equals(Pit.PitType.HOUSE)
                && getOppositePit(pit).getSeeds() > 0;
    }

    private void doCapture(Pit pit) {
        Pit oppositePit = getOppositePit(pit);
        pit.clearSeeds();

        // + 1 to account for the seed which caused the capture
        currentPlayer.getStore().addSeeds(oppositePit.getSeeds() + 1);
        oppositePit.clearSeeds();

        switchPlayer();
    }

    private int getTotalSeeds() {
        return Constants.NUM_PLAYERS * Constants.NUM_PITS * Constants.NUM_SEEDS;
    }
}
