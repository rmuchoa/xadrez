package boardgame;

public class BoardPosition {

    private final int row;
    private final int column;

    public BoardPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return "board[" + row + "][" + column + "]";
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof BoardPosition
            && equals((BoardPosition) any);
    }

    private boolean equals(BoardPosition position) {
        return position != null
            && hasEqualRow(position)
            && hasEqualColumn(position);
    }

    private boolean hasEqualRow(BoardPosition position) {
        return row == position.getRow();
    }

    private boolean hasEqualColumn(BoardPosition position) {
        return column == position.getColumn();
    }

    public static class BoardPositionBuilder {

        private int row;
        private int column;

        private BoardPositionBuilder() {}

        public static BoardPositionBuilder builder() {
            return new BoardPositionBuilder();
        }

        public BoardPositionBuilder row(int row) {
            this.row = row;
            return this;
        }

        public BoardPositionBuilder column(int column) {
            this.column = column;
            return this;
        }

        public BoardPosition build() {
            return new BoardPosition(row, column);
        }

    }

}
