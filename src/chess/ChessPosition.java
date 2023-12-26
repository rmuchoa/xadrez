package chess;

import static chess.ChessBoard.CHESS_BOARD_SIZE;

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

    public ChessPosition getNextNorthPosition() {
        return builder()
            .chessColumn(getSameChessColumn())
            .chessRow(getNextNorthChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextSouthPosition() {
        return builder()
            .chessColumn(getSameChessColumn())
            .chessRow(getNextSouthChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextEastPosition() {
        return builder()
            .chessColumn(getNextEastChessColumn())
            .chessRow(getSameChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextWestPosition() {
        return builder()
            .chessColumn(getNextWestChessColumn())
            .chessRow(getSameChessRow())
            .lineMovement()
            .build();
    }

    public ChessPosition getNextNorthEastPosition() {
        return builder()
            .chessColumn(getNextEastChessColumn())
            .chessRow(getNextNorthChessRow())
            .diagonalMovement()
            .build();
    }

    public ChessPosition getNextNorthWestPosition() {
        return builder()
            .chessColumn(getNextWestChessColumn())
            .chessRow(getNextNorthChessRow())
            .diagonalMovement()
            .build();
    }

    public ChessPosition getNextSouthEastPosition() {
        return builder()
            .chessColumn(getNextEastChessColumn())
            .chessRow(getNextSouthChessRow())
            .diagonalMovement()
            .build();
    }

    public ChessPosition getNextSouthWestPosition() {
        return builder()
            .chessColumn(getNextWestChessColumn())
            .chessRow(getNextSouthChessRow())
            .diagonalMovement()
            .build();
    }

    public ChessPosition getSuperiorNorthEastElMovementPosition() {
        return builder()
            .chessColumn(getNextEastChessColumn())
            .chessRow(getTwoAheadNorthChessRow())
            .elMovement()
            .build();
    }

    public ChessPosition getSuperiorNorthWestElMovementPosition() {
        return builder()
            .chessColumn(getNextWestChessColumn())
            .chessRow(getTwoAheadNorthChessRow())
            .elMovement()
            .build();
    }

    public ChessPosition getInferiorNorthEastElMovementPosition() {
        return builder()
            .chessColumn(getTwoBesideEastChessColumn())
            .chessRow(getNextNorthChessRow())
            .elMovement()
            .build();
    }

    public ChessPosition getInferiorNorthWestElMovementPosition() {
        return builder()
            .chessColumn(getTwoBesideWestChessColumn())
            .chessRow(getNextNorthChessRow())
            .elMovement()
            .build();
    }

    public ChessPosition getSuperiorSouthEastElMovementPosition() {
        return builder()
            .chessColumn(getTwoBesideEastChessColumn())
            .chessRow(getNextSouthChessRow())
            .elMovement()
            .build();
    }

    public ChessPosition getSuperiorSouthWestElMovementPosition() {
        return builder()
            .chessColumn(getTwoBesideWestChessColumn())
            .chessRow(getNextSouthChessRow())
            .elMovement()
            .build();
    }

    public ChessPosition getInferiorSouthEastElMovementPosition() {
        return builder()
            .chessColumn(getNextEastChessColumn())
            .chessRow(getTwoBehindSouthChessRow())
            .elMovement()
            .build();
    }

    public ChessPosition getInferiorSouthWestElMovementPosition() {
        return builder()
            .chessColumn(getNextWestChessColumn())
            .chessRow(getTwoBehindSouthChessRow())
            .elMovement()
            .build();
    }

    private char getSameChessColumn() {
        return chessColumn;
    }

    private char getNextEastChessColumn() {
        char column = chessColumn;
        return ++column;
    }

    private char getNextWestChessColumn() {
        char column = chessColumn;
        return --column;
    }

    private char getTwoBesideEastChessColumn() {
        char column = chessColumn;
        column += 2;

        return column;
    }

    private char getTwoBesideWestChessColumn() {
        char column = chessColumn;
        column -= 2;

        return column;
    }

    private int getSameChessRow() {
        return chessRow;
    }

    private int getNextNorthChessRow() {
        return chessRow + 1;
    }

    private int getNextSouthChessRow() {
        return chessRow - 1;
    }

    private int getTwoAheadNorthChessRow() {
        return chessRow + 2;
    }

    private int getTwoBehindSouthChessRow() {
        return chessRow - 2;
    }

    @Override
    public String toString() {
        return "" + chessColumn + chessRow;
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof ChessPosition &&
            equalsPosition((ChessPosition) any);
    }

    private boolean equalsPosition(ChessPosition position) {
        return super.equals(position) &&
            chessColumn == position.getChessColumn() &&
            chessRow == position.getChessRow();
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
