package board.dummy;

import board.BoardPosition;
import board.dummy.builder.DummyBoardPositionBuilder;

public class DummyBoardPosition extends BoardPosition {

    public DummyBoardPosition(int matrixRow, int matrixColumn) {
        super(matrixRow, matrixColumn);
    }

    public static DummyBoardPositionBuilder builder() {
        return DummyBoardPositionBuilder.builder();
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof DummyBoardPosition
            && super.equals(any);
    }

}
