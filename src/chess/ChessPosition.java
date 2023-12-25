package chess;

import static chess.ChessMatch.CHESS_BOARD_SIZE;

import boardgame.BoardPosition;

public class ChessPosition extends BoardPosition {

    private final char chessColumn;
    private final int chessRow;
    private final MovementType movement;

    public ChessPosition(char chessColumn, int chessRow) {
        this(chessColumn, chessRow, null);
    }
    
    public ChessPosition(char chessColumn, int chessRow, MovementType movement) {
        super(toBoardMatrixRow(chessRow), toBoardMatrixColumn(chessColumn));

        validateChessPositionCoordinate(chessColumn, chessRow);

        this.chessColumn = chessColumn;
        this.chessRow = chessRow;
        this.movement = movement;
    }

    public char getChessColumn() {
        return chessColumn;
    }

    public int getChessRow() {
        return chessRow;
    }

    public MovementType getMovement() {
        return movement;
    }

    public ChessPosition getNextAbovePosition() {
        return builder()
            .chessColumn(getSameChessColumn())
            .chessRow(getNextAboveChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextBelowPosition() {
        return builder()
            .chessColumn(getSameChessColumn())
            .chessRow(getNextBelowChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextRightPosition() {
        return builder()
            .chessColumn(getNextRightChessColumn())
            .chessRow(getSameChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextLeftPosition() {
        return builder()
            .chessColumn(getNextLeftChessColumn())
            .chessRow(getSameChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextDiagonalSuperiorRightPosition() {
        return builder()
            .chessColumn(getNextRightChessColumn())
            .chessRow(getNextAboveChessRow())
            .diagonalMovement()
            .build();
    }

    public ChessPosition getNextDiagonalSuperiorLeftPosition() {
        return builder()
            .chessColumn(getNextLeftChessColumn())
            .chessRow(getNextAboveChessRow())
            .diagonalMovement()
            .build();
    }

    public ChessPosition getNextDiagonalInferiorRightPosition() {
        return builder()
            .chessColumn(getNextRightChessColumn())
            .chessRow(getNextBelowChessRow())
            .diagonalMovement()
            .build();
    }

    public ChessPosition getNextDiagonalInferiorLeftPosition() {
        return builder()
            .chessColumn(getNextLeftChessColumn())
            .chessRow(getNextBelowChessRow())
            .diagonalMovement()
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
            && equalsPosition((ChessPosition) any);
    }

    private boolean equalsPosition(ChessPosition position) {
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

    public enum MovementType {

        LINE,
        DIAGONAL,
        EL

    }

    public static class ChessPositionBuilder {

        private char chessColumn;
        private int chessRow;
        private MovementType movement;

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

        public ChessPositionBuilder movement(MovementType movement) {
            this.movement = movement;
            return this;
        }

        public ChessPositionBuilder lineMovement() {
            return movement(MovementType.LINE);
        }

        public ChessPositionBuilder diagonalMovement() {
            return movement(MovementType.DIAGONAL);
        }

        public ChessPositionBuilder elMovement() {
            return movement(MovementType.EL);
        }

        public ChessPositionBuilder boardPostion(int row, int column) {
            this.chessColumn = (char) ('a' + column);
            this.chessRow = CHESS_BOARD_SIZE - row;
            return this;
        }

        public ChessPosition build() {
            return new ChessPosition(chessColumn, chessRow, movement);
        }

    }

}
