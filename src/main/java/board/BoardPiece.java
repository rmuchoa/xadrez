package board;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BoardPiece <T extends BoardPosition, P extends BoardPiece<T, P, B>, B extends Board<T, P, B>> {

    private T position;
    private B board;

    protected BoardPiece() {}

    public void placeOnPosition(T position, B board) {
        this.position = position;
        this.board = board;
        applyMatch();
    }

    protected abstract void applyMatch();

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
