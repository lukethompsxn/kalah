package kalah.action;

import kalah.util.*;

import java.util.List;

public class GameAction implements Action {
    private GameBoard gameBoard;
    private House house;
    private int index;

    public GameAction(GameBoard gameBoard, Player player, House house) {
        this.gameBoard = gameBoard;
        this.house = house;
        this.index = player.getOffset() + house.getId();
    }

    @Override
    public void execute() {
        int seedCount = house.getSeeds();
        house.clearSeeds();

        Pit pit = null;
        for (int i = 0; i < seedCount; i++) {
            pit = getNextPit();
            pit.addSeed();
        }

        if (pit == null) {
            return;
        } else if (!gameBoard.getCurrentPlayer().getHouses().containsValue(pit) // Case 1
                || pit.getSeeds() > 1) {
            gameBoard.switchPlayer();

        } else if (pit.equals(gameBoard.getCurrentPlayer().getStore())) { // Case 2
            // do nothing

        } else if (gameBoard.getCurrentPlayer().getHouses().containsValue(pit) // Case 3
                && pit.getSeeds() == 0
                && pit instanceof House){
            House oppositePit = getOppositePit((House)pit); //todo fix casting
            gameBoard.getCurrentPlayer().getStore().addSeeds(oppositePit.getSeeds());
            oppositePit.clearSeeds();

        } else {
            gameBoard.switchPlayer();
        }
    }

    private Pit getNextPit() {
        List<Pit> boardPits = gameBoard.getBoardPits();

        if (isOpponentsStore(index, boardPits)) {
            index++;
        }

        if (index >= boardPits.size()) {
            index = 0;
        }

        index++;

        return boardPits.get(index - 1);
    }

    private boolean isOpponentsStore(int index, List<Pit> boardPits) {
        return index < boardPits.size()
                && boardPits.get(index) != null
                && boardPits.get(index) instanceof Store
                && !boardPits.get(index).equals(gameBoard.getCurrentPlayer().getStore());
    }

    private House getOppositePit(House currentPit) {
        int numPits = gameBoard.getP1().getHouses().size();
        return gameBoard.getOpponentPlayer().getHouses().get(numPits + 1 - currentPit.getId());
    }
}
