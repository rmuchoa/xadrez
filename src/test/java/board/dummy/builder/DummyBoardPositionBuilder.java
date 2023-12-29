package board.dummy.builder;

import board.dummy.DummyBoardPosition;

public class DummyBoardPositionBuilder {

    public static final int FIRST_MATRIX_ROW = 0;
    public static final int SECOND_MATRIX_ROW = 1;
    public static final int FIRST_MATRIX_COLUMN = 0;
    public static final int SECOND_MATRIX_COLUMN = 1;

    private int matrixRow;
    private int matrixColumn;

    private DummyBoardPositionBuilder() {}

    public static DummyBoardPositionBuilder builder() {
        return new DummyBoardPositionBuilder();
    }

    public DummyBoardPositionBuilder filled() {
        return matrixRow().matrixColumn();
    }

    public DummyBoardPositionBuilder matrixRow() {
        return matrixRow(FIRST_MATRIX_ROW);
    }

    public DummyBoardPositionBuilder matrixRow(int matrixRow) {
        this.matrixRow = matrixRow;
        return this;
    }

    public DummyBoardPositionBuilder matrixColumn() {
        return matrixColumn(FIRST_MATRIX_COLUMN);
    }

    public DummyBoardPositionBuilder matrixColumn(int matrixColumn) {
        this.matrixColumn = matrixColumn;
        return this;
    }

    public DummyBoardPosition build() {
        return new DummyBoardPosition(matrixRow, matrixColumn);
    }

}
