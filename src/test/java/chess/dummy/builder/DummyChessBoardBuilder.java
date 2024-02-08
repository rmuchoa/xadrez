package chess.dummy.builder;

import board.dummy.builder.DummyBoardPieceBuilder;
import chess.ChessMatch;
import chess.dummy.DummyChessBoard;

public class DummyChessBoardBuilder {

    private ChessMatch match;

    private DummyChessBoardBuilder() {}

    public static DummyChessBoardBuilder builder() {
        return new DummyChessBoardBuilder();
    }

    public DummyChessBoardBuilder match(ChessMatch match) {
        this.match = match;
        return this;
    }

    public DummyChessBoard build() {
        return new DummyChessBoard(match);
    }
}
