package kalah.action;

import kalah.exception.InvalidActionException;

public interface Action {
    void execute() throws InvalidActionException;
}
