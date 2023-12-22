package chess;

import boardgame.Position;

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

    protected Position toBoardPosition() {
        return new Position(toBoardPositionRow(), toBoardPositionColumn());
    }

    private int toBoardPositionRow() {
        return 8 - row;
    }

    private int toBoardPositionColumn() {
        return column - 'a';
    }

    protected static ChessPosition fromPosition(Position position) {
        return new ChessPosition(toChessPositionColumn(position), toChessPositionRow(position));
    }

    private static char toChessPositionColumn(Position position) {
        return (char) ('a' - position.getColumn());
    }

    private static int toChessPositionRow(Position position) {
        return 8 - position.getRow();
    }

}
