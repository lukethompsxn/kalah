package kalah.util;

import kalah.exception.InvalidActionException;

import java.util.Map;

public interface GameBoard {
    Player getP1();
    Player getP2();
    Player getCurrentPlayer();
    Player getOpponentPlayer();
    boolean isActive();
    void terminate();
    void executeMove(int position) throws InvalidActionException;
    boolean canContinue();
    boolean isGameCompleted();
    Map<Player, Integer> getFinalScores();
}
