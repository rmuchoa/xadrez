package board;

import lombok.Getter;

@Getter
public class BoardPosition {

    private final int matrixRow;
    private final int matrixColumn;

    public BoardPosition(int matrixRow, int matrixColumn) {
        this.matrixRow = matrixRow;
        this.matrixColumn = matrixColumn;
    }

    @Override
    public String toString() {
        return "board[" + matrixRow + "][" + matrixColumn + "]";
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof BoardPosition position
            && equalsPosition(position);
    }

    private boolean equalsPosition(BoardPosition position) {
        return position != null
            && matrixRow == position.getMatrixRow()
            && matrixColumn == position.getMatrixColumn();
    }

}
