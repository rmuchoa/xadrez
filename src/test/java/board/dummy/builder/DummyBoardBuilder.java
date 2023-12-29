package board.dummy.builder;

import board.dummy.DummyBoard;
import board.dummy.DummyBoardPiece;
import board.dummy.DummyBoardPosition;

public class DummyBoardBuilder {

    public static final int TOTAL_ROWS = 8;
    public static final int TOTAL_COLUMNS = 8;

    private int totalRows;
    private int totalColumns;
    private DummyBoardPiece placedPiece;
    private DummyBoardPosition placedPosition;

    private DummyBoardBuilder() {}

    public static DummyBoardBuilder builder() {
        return new DummyBoardBuilder();
    }

    public DummyBoardBuilder filled() {
        return totalRows().totalColumns();
    }

    public DummyBoardBuilder withPlacedPiece(DummyBoardPiece piece, DummyBoardPosition position) {
        this.placedPosition = position;
        this.placedPiece = piece;
        return this;
    }

    public DummyBoardBuilder totalRows() {
        return totalRows(TOTAL_ROWS);
    }

    public DummyBoardBuilder totalRows(int totalRows) {
        this.totalRows = totalRows;
        return this;
    }

    public DummyBoardBuilder totalColumns() {
        return totalColumns(TOTAL_COLUMNS);
    }

    public DummyBoardBuilder totalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
        return this;
    }

    public DummyBoard build() {
        DummyBoard dummyBoard = new DummyBoard(totalRows, totalColumns);

        if (placedPosition != null && placedPiece != null)
            dummyBoard.placePieceOn(placedPosition, placedPiece);

        return dummyBoard;
    }

}
