package kalah.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private boolean isActive;
    private boolean gameCompleted;
    private List<Pit> boardPits;
    private int totalSeeds;

    public GameBoard(Player p1, Player p2, List<Pit> boardPits, int totalSeeds) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        this.isActive = true;
        this.gameCompleted = false;
        this.boardPits = boardPits;
        this.totalSeeds = totalSeeds;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getOpponentPlayer() {
        if (currentPlayer.equals(p1)) {
            return p2;
        } else {
            return p1;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void terminate() {
        isActive = false;
    }

    public List<Pit> getBoardPits() {
        return boardPits;
    }

    public void switchPlayer() {
        if (currentPlayer.equals(p1)) {
            currentPlayer = p2;
        } else {
            currentPlayer = p1;
        }
    }

    public boolean canContinue() {
        for (Pit house : currentPlayer.getHouses().values()) {
            if (house.getSeeds() > 0) {
                return true;
            }
        }

        gameCompleted = true;
        return false;
    }

    public boolean isGameCompleted() {
        return gameCompleted;
    }

    public Map<Player, Integer> getFinalScores() {
        Map<Player, Integer> scores = new HashMap<>();
        scores.put(getOpponentPlayer(), totalSeeds - currentPlayer.getStore().getSeeds());
        scores.put(currentPlayer, currentPlayer.getStore().getSeeds());
        return scores;
    }
}
