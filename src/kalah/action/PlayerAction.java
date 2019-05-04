package kalah.action;

import kalah.exception.InvalidActionException;
import kalah.util.*;

import java.util.List;

public class PlayerAction implements Action {
    private GameBoard gameBoard;
    private House house;
    private int index;

    public PlayerAction(GameBoard gameBoard, Player player, House house) {
        this.gameBoard = gameBoard;
        this.house = house;
        this.index = player.getOffset() + house.getId();
    }

    @Override
    public void execute() throws InvalidActionException {
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
            gameBoard.switchPlayer();
        } else if (isCapture(pit)) {
            doCapture(pit);
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

    //todo refactor to just use getOpponent().getStore()
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

    // Case 1
    private boolean isOpponentsTurn(Pit pit) {
        return !gameBoard.getCurrentPlayer().getHouses().containsValue(pit)
                || pit.getSeeds() > 1;
    }

    // Case 2
    private boolean isPlayersOwnStore(Pit pit) {
        return pit.equals(gameBoard.getCurrentPlayer().getStore());
    }

    // Case 3
    private boolean isCapture(Pit pit) {
        return gameBoard.getCurrentPlayer().getHouses().containsValue(pit)
                && pit.getSeeds() == 1
                && pit instanceof House
                && getOppositePit((House)pit).getSeeds() > 0;
    }

    private void doCapture(Pit pit) {
        House oppositePit = getOppositePit((House)pit); //todo fix casting
        gameBoard.getCurrentPlayer().getStore().addSeeds(oppositePit.getSeeds());
        oppositePit.clearSeeds();
        ((House) pit).clearSeeds();
        gameBoard.getCurrentPlayer().getStore().addSeed();
        gameBoard.switchPlayer();
    }
}
