package boardgame;

import java.util.List;

public abstract class BoardPiece {

    private final Board board;
    private BoardPosition position;

    public BoardPiece(Board board) {
        this.board = board;
    }

    protected Board getBoard() {
        return board;
    }

    public BoardPosition getPosition() {
        return position;
    }

    public void placeOnPosition(BoardPosition position) {
        this.position = position;
    }

    public void takeOutOfPosition() {
        position = null;
    }

    public abstract List<BoardPosition> getAllAvailableTargetPositions();

    public boolean isNotAnAvailableTarget(BoardPosition target) {
        return !isAnAvailableTarget(target);
    }

    public boolean isAnAvailableTarget(BoardPosition target) {
        return getAllAvailableTargetPositions().stream()
            .anyMatch(target::equals);
    }

    public boolean thereIsNoneAvailableTargetPosition() {
        return getAllAvailableTargetPositions().stream()
            .findAny()
            .isEmpty();
    }

    protected BoardPiece getBoardPiecePlacedOn(BoardPosition position) {
        return board.getPiecePlacedOn(position);
    }

    protected boolean doesNotExistsOnBoard(BoardPosition position) {
        return board.doesNotExists(position);
    }

}
