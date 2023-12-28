package board.mock;

import board.BoardPosition;
import board.mock.MockBoard.MockBoardBuilder;

public class MockBoardPosition extends BoardPosition {

    private static final int FIRST_MATRIX_ROW = 0;
    private static final int SECOND_MATRIX_ROW = 1;
    private static final int FIRST_MATRIX_COLUMN = 0;
    private static final int SECOND_MATRIX_COLUMN = 1;

    public MockBoardPosition(int matrixRow, int matrixColumn) {
        super(matrixRow, matrixColumn);
    }

    public static MockBoardPositionBuilder builder() {
        return MockBoardPositionBuilder.builder();
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof MockBoardPosition
            && super.equals(any);
    }

    public static class MockBoardPositionBuilder {

        private int matrixRow;
        private int matrixColumn;

        private MockBoardPositionBuilder() {}

        public static MockBoardPositionBuilder builder() {
            return new MockBoardPositionBuilder();
        }

        public MockBoardPositionBuilder filled() {
            return matrixRow().matrixColumn();
        }

        public MockBoardPositionBuilder matrixRow() {
            return matrixRow(FIRST_MATRIX_ROW);
        }

        public MockBoardPositionBuilder anotherMatrixRow() {
            return matrixRow(SECOND_MATRIX_ROW);
        }

        public MockBoardPositionBuilder matrixRow(int matrixRow) {
            this.matrixRow = matrixRow;
            return this;
        }

        public MockBoardPositionBuilder matrixColumn() {
            return matrixColumn(FIRST_MATRIX_COLUMN);
        }

        public MockBoardPositionBuilder anotherMatrixColumn() {
            return matrixColumn(SECOND_MATRIX_COLUMN);
        }

        public MockBoardPositionBuilder matrixColumn(int matrixColumn) {
            this.matrixColumn = matrixColumn;
            return this;
        }

        public MockBoardPosition build() {
            return new MockBoardPosition(matrixRow, matrixColumn);
        }

        public StubMockBoardPosition stub() {
            return new StubMockBoardPosition(matrixRow, matrixColumn);
        }
    }

    public static class StubMockBoardPosition extends MockBoardPosition {

        public StubMockBoardPosition(int matrixRow, int matrixColumn) {
            super(matrixRow, matrixColumn);
        }

        @Override
        public boolean equals(Object any) {
            return any instanceof StubMockBoardPosition
                && super.equals(any);
        }
    }

}
