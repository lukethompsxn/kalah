package kalah.action;

import kalah.util.GameBoard;
import kalah.util.Pit;
import kalah.util.Player;

public class GameAction implements Action {
    private GameBoard gameBoard;
    private Player player;
    private Pit house;

    public GameAction(GameBoard gameBoard, Player player, Pit house) {
        this.gameBoard = gameBoard;
        this.player = player;
        this.house = house;
    }

    @Override
    public void execute() {
        System.out.println(player.getId() + " " + house.getSeeds());
    }
}
