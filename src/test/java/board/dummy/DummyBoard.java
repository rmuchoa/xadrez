package board.dummy;

import board.Board;
import board.dummy.builder.DummyBoardBuilder;

public class DummyBoard extends Board<DummyBoardPosition, DummyBoardPiece, DummyBoard> {

    public DummyBoard(int totalRows, int totalColumns) {
        super(totalRows, totalColumns);
    }

    public static DummyBoardBuilder dummyBuilder() {
        return DummyBoardBuilder.builder();
    }

}
