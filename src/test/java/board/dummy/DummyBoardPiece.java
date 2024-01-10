package board.dummy;

import board.BoardPiece;
import board.dummy.builder.DummyBoardPieceBuilder;
import chess.ChessMatch;
import java.util.Collections;
import java.util.List;

public class DummyBoardPiece extends BoardPiece<DummyBoardPosition, DummyBoardPiece, DummyBoard> {

    public DummyBoardPiece() {
        super();
    }

    public DummyBoardPiece(DummyBoard board, DummyBoardPosition position) {
        super();
        placeOnPosition(position, board);
    }

    @Override
    protected void applyMatch() {}

    @Override
    public boolean equals(Object any) {
        return any instanceof DummyBoardPiece dummy
            && super.equals(dummy);
    }

    public static DummyBoardPieceBuilder builder() {
        return DummyBoardPieceBuilder.builder();
    }

}
