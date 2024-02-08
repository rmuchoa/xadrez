package board.dummy;

import board.BoardPiece;
import board.dummy.builder.DummyBoardPieceBuilder;

public class DummyBoardPiece extends BoardPiece<DummyBoardPosition, DummyBoardPiece, DummyBoard> {

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
