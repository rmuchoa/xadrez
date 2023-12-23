package boardgame;

import java.util.List;

public abstract class BoardPiece <T extends BoardPosition> {

    private final Board board;
    private T position;

    public BoardPiece(Board board) {
        this.board = board;
    }

    protected Board getBoard() {
        return board;
    }

    public T getPosition() {
        return position;
    }

    public void placeOnPosition(T position) {
        this.position = position;
    }

    public void takeOutOfPosition() {
        position = null;
    }

    public abstract List<T> getAllAvailableTargetPositions();

    public boolean canNotTargetThis(T position) {
        return !canTargetThis(position);
    }

    public boolean canTargetThis(T position) {
        return getAllAvailableTargetPositions().stream()
            .anyMatch(position::equals);
    }

    public boolean hasNoAvailableMovements() {
        return getAllAvailableTargetPositions().stream()
            .findAny()
            .isEmpty();
    }

    protected BoardPiece getBoardPiecePlacedOn(T position) {
        return board.getPiecePlacedOn(position);
    }

    protected boolean doesNotExistsOnBoard(T position) {
        return board.doesNotExists(position);
    }

}
