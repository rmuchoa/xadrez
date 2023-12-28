package board;

import java.util.List;

public abstract class BoardPiece <T extends BoardPosition, P extends BoardPiece<T, P, B>, B extends Board<T, P, B>> {

    private final B board;
    private T position;

    protected BoardPiece(B board) {
        this.board = board;
    }

    protected B getBoard() {
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

    protected P getBoardPiecePlacedOn(T position) {
        return board.getPiecePlacedOn(position);
    }

    protected boolean doesExistsOnBoard(T position) {
        return board.doesExists(position);
    }

    protected boolean doesNotExistsOnBoard(T position) {
        return board.doesNotExists(position);
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
        return getAllAvailableTargetPositions().isEmpty();
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof BoardPiece
            && position.equals(((BoardPiece<?, ?, ?>) any).getPosition());
    }

    @Override
    public String toString() {
        return position.toString();
    }
}
