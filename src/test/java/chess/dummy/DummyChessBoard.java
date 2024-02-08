package chess.dummy;

import chess.ChessBoard;
import chess.ChessMatch;
import chess.dummy.builder.DummyChessBoardBuilder;

public class DummyChessBoard extends ChessBoard {

    public DummyChessBoard(ChessMatch match) {
        super(match);
    }

    public static DummyChessBoardBuilder builder() {
        return DummyChessBoardBuilder.builder();
    }
}
