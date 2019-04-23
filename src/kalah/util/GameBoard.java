package kalah.util;

public class GameBoard {
    private Player p1;
    private Player p2;

    public GameBoard(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public Player getCurrentPlayer() {
        return p1; //todo implement this
    }
}