package kalah.action;

import kalah.exception.InvalidMoveException;

public interface Action {
    void execute() throws InvalidMoveException;
}
