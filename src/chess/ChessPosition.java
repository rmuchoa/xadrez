package chess;

import static chess.ChessMatch.CHESS_BOARD_SIZE;

import boardgame.BoardPosition;

public class ChessPosition extends BoardPosition {

    private final char chessColumn;
    private final int chessRow;

    public ChessPosition(char chessColumn, int chessRow) {
        super(toBoardMatrixRow(chessRow), toBoardMatrixColumn(chessColumn));

        validateChessPositionCoordinate(chessColumn, chessRow);

        this.chessColumn = chessColumn;
        this.chessRow = chessRow;
    }

    public char getChessColumn() {
        return chessColumn;
    }

    public int getChessRow() {
        return chessRow;
    }

    public ChessPosition getNextAbovePosition() {
        return builder()
            .chessColumn(getSameChessColumn())
            .chessRow(getNextAboveChessRow())
            .build();
    }

    public ChessPosition getNextBelowPosition() {
        return builder()
            .chessColumn(getSameChessColumn())
            .chessRow(getNextBelowChessRow())
            .build();
    }

    public ChessPosition getNextRightPosition() {
        return builder()
            .chessColumn(getNextRightChessColumn())
            .chessRow(getSameChessRow())
            .build();
    }

    public ChessPosition getNextLeftPosition() {
        return builder()
            .chessColumn(getNextLeftChessColumn())
            .chessRow(getSameChessRow())
            .build();
    }

    public ChessPosition getNextDiagonalSuperiorRightPosition() {
        return builder()
            .chessColumn(getNextRightChessColumn())
            .chessRow(getNextAboveChessRow())
            .build();
    }

    public ChessPosition getNextDiagonalSuperiorLeftPosition() {
        return builder()
            .chessColumn(getNextLeftChessColumn())
            .chessRow(getNextAboveChessRow())
            .build();
    }

    public ChessPosition getNextDiagonalInferiorRightPosition() {
        return builder()
            .chessColumn(getNextRightChessColumn())
            .chessRow(getNextBelowChessRow())
            .build();
    }

    public ChessPosition getNextDiagonalInferiorLeftPosition() {
        return builder()
            .chessColumn(getNextLeftChessColumn())
            .chessRow(getNextBelowChessRow())
            .build();
    }

    private char getSameChessColumn() {
        return chessColumn;
    }

    private char getNextRightChessColumn() {
        char right = chessColumn;
        return ++right;
    }

    private char getNextLeftChessColumn() {
        char right = chessColumn;
        return --right;
    }

    private int getSameChessRow() {
        return chessRow;
    }

    private int getNextAboveChessRow() {
        return chessRow + 1;
    }

    private int getNextBelowChessRow() {
        return chessRow - 1;
    }

    @Override
    public String toString() {
        return "" + chessColumn + chessRow;
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof ChessPosition
            && equals((ChessPosition) any);
    }

    private boolean equals(ChessPosition position) {
        return super.equals(position)
            && chessColumn == position.getChessColumn()
            && chessRow == position.getChessRow();
    }

    private static int toBoardMatrixRow(int chessRow) {
        return CHESS_BOARD_SIZE - chessRow;
    }

    private static int toBoardMatrixColumn(char chessColumn) {
        return chessColumn - 'a';
    }

    private static void validateChessPositionCoordinate(char chessColumn, int chessRow) {
        if (isNotAValidChessPositionCoordinate(chessColumn, chessRow))
            throw new ChessException("Error instantiating ChessPosition "+chessColumn+chessRow+". Valid values are from a1 to h8.");
    }

    private static boolean isNotAValidChessPositionCoordinate(char chessColumn, int chessRow) {
        return isNotAChessColumnLetter(chessColumn) ||
            isNotAChessRowNumber(chessRow);
    }

    private static boolean isNotAChessColumnLetter(char chessColumn) {
        return chessColumn < 'a' || chessColumn > 'h';
    }

    private static boolean isNotAChessRowNumber(int chessRow) {
        return chessRow < 1 || chessRow > CHESS_BOARD_SIZE;
    }

    public static ChessPositionBuilder builder() {
        return ChessPositionBuilder.builder();
    }

    public static class ChessPositionBuilder {

        private char chessColumn;
        private int chessRow;

        private ChessPositionBuilder() {}

        public static ChessPositionBuilder builder() {
            return new ChessPositionBuilder();
        }

        public ChessPositionBuilder chessColumn(char chessColumn) {
            this.chessColumn = chessColumn;
            return this;
        }

        public ChessPositionBuilder chessRow(int chessRow) {
            this.chessRow = chessRow;
            return this;
        }

        public ChessPositionBuilder boardPostion(int row, int column) {
            this.chessColumn = (char) ('a' + column);
            this.chessRow = CHESS_BOARD_SIZE - row;
            return this;
        }

        public ChessPosition build() {
            return new ChessPosition(chessColumn, chessRow);
        }

    }

}
