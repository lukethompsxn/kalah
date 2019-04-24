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

    public boolean isActive() {
        return isActive;
    }

    public void terminate() {
        isActive = false;
    }

    public List<Pit> getBoardPits() {
        return boardPits;
    }
}
