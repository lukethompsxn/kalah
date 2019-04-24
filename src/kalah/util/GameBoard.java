package kalah.util;

import java.util.List;

public class GameBoard {
    private Player p1;
    private Player p2;
    private Player currentPlayer;
    private boolean isActive;
    private List<Pit> boardPits;

    public GameBoard(Player p1, Player p2, List<Pit> boardPits) {
        this.p1 = p1;
        this.p2 = p2;
        this.currentPlayer = p1;
        this.isActive = true;
        this.boardPits = boardPits;
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
        for (House house : currentPlayer.getHouses().values()) {
            if (house.getSeeds() > 0) {
                return true;
            }
        }
        return false;
    }
}
