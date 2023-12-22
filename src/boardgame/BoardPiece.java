package boardgame;

public class BoardPiece {

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
}
