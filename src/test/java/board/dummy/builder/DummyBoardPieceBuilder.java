package board.dummy.builder;

import board.dummy.DummyBoard;
import board.dummy.DummyBoardPiece;
import board.dummy.DummyBoardPosition;

public class DummyBoardPieceBuilder {

    private DummyBoard board;
    private DummyBoardPosition position;
    private DummyBoardPosition availableTargetPosition;

    private DummyBoardPieceBuilder() {}

    public static DummyBoardPieceBuilder builder() {
        return new DummyBoardPieceBuilder();
    }

    public DummyBoardPieceBuilder position(DummyBoardPosition position) {
        this.position = position;
        return this;
    }

    public DummyBoardPieceBuilder board(DummyBoard board) {
        this.board = board;
        return this;
    }

    public DummyBoardPieceBuilder availableTargetPosition(DummyBoardPosition position) {
        this.availableTargetPosition = position;
        return this;
    }

    public DummyBoardPiece build() {
        return new DummyBoardPiece(board, position, availableTargetPosition);
    }

}
