package kalah.action;

import kalah.util.*;

import java.util.List;

public class GameAction implements Action {
    private GameBoard gameBoard;
    private Player player;
    private House house;
    private int index;

    public GameAction(GameBoard gameBoard, Player player, House house) {
        this.gameBoard = gameBoard;
        this.player = player;
        this.house = house;
        this.index = player.getOffset() + house.getId();
    }

    @Override
    public void execute() {
        int seedCount = house.getSeeds();
        house.clearSeeds();

        for (int i = 0; i < seedCount; i++) {
            getNextPit().addSeed();
        }

        //todo implement all the conditions here
    }

    private Pit getNextPit() {
        List<Pit> boardPits = gameBoard.getBoardPits();

        if (index > boardPits.size()) {
            index = 0;
        }

        index++;

        return boardPits.get(index - 1);
    }
}
