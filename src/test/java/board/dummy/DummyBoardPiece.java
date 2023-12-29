package board.dummy;

import board.BoardPiece;
import board.dummy.builder.DummyBoardPieceBuilder;
import java.util.Collections;
import java.util.List;

public class DummyBoardPiece extends BoardPiece<DummyBoardPosition, DummyBoardPiece, DummyBoard> {

    private DummyBoardPosition availableTargetPosition;

    public DummyBoardPiece(DummyBoard board) {
        super(board);
    }

    public DummyBoardPiece(DummyBoard board, DummyBoardPosition position, DummyBoardPosition availableTargetPosition) {
        super(board);
        placeOnPosition(position);
        this.availableTargetPosition = availableTargetPosition;
    }

    @Override
    public List<DummyBoardPosition> getAllAvailableTargetPositions() {
        if (availableTargetPosition != null) {
            return Collections.singletonList(availableTargetPosition);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof DummyBoardPiece
            && super.equals(any);
    }

    public static DummyBoardPieceBuilder builder() {
        return DummyBoardPieceBuilder.builder();
    }

}
