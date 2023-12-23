package boardgame;

public class BoardPosition {

    private final int matrixRow;
    private final int matrixColumn;

    public BoardPosition(int matrixRow, int matrixColumn) {
        this.matrixRow = matrixRow;
        this.matrixColumn = matrixColumn;
    }

    public int getMatrixRow() {
        return matrixRow;
    }

    public int getMatrixColumn() {
        return matrixColumn;
    }

    @Override
    public String toString() {
        return "board[" + matrixRow + "][" + matrixColumn + "]";
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof BoardPosition
            && equals((BoardPosition) any);
    }

    private boolean equals(BoardPosition position) {
        return position != null
            && matrixRow == position.getMatrixRow()
            && matrixColumn == position.getMatrixColumn();
    }

}
