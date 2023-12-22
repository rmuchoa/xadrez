package chess;

import boardgame.BoardPosition;

public class ChessPosition {

    private final char column;
    private final int row;

    public ChessPosition(char column, int row) {
        validateChessPositionCoordinate(column, row);

        this.column = column;
        this.row = row;
    }

    public char getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    private static void validateChessPositionCoordinate(char column, int row) {
        if (isNotAValidChessPositionCoordinate(column, row))
            throw new ChessException("Error instantiating ChessPosition "+column+row+". Valid values are from a1 to h8. ");
    }

    private static boolean isNotAValidChessPositionCoordinate(char column, int row) {
        return isNotAChessBoardColumnLetter(column) ||
            isNotAChessBoardRowNumber(row);
    }

    private static boolean isNotAChessBoardColumnLetter(char column) {
        return column < 'a' || column > 'h';
    }

    private static boolean isNotAChessBoardRowNumber(int row) {
        return row < 1 || row > 8;
    }

    protected BoardPosition toBoardPosition() {
        return new BoardPosition(toBoardPositionRow(), toBoardPositionColumn());
    }

    private int toBoardPositionRow() {
        return 8 - row;
    }

    private int toBoardPositionColumn() {
        return column - 'a';
    }

    protected static ChessPosition fromPosition(BoardPosition boardPosition) {
        return new ChessPosition(toChessPositionColumn(boardPosition), toChessPositionRow(boardPosition));
    }

    private static char toChessPositionColumn(BoardPosition boardPosition) {
        return (char) ('a' - boardPosition.getColumn());
    }

    private static int toChessPositionRow(BoardPosition boardPosition) {
        return 8 - boardPosition.getRow();
    }

}
