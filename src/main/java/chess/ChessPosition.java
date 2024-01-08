package chess;

import static chess.ChessBoard.CHESS_BOARD_SIZE;

import board.BoardPosition;
import lombok.Getter;

@Getter
public class ChessPosition extends BoardPosition {

    private final char chessColumn;
    private final int chessRow;

    public ChessPosition(int row, int column) {
        this(toChessColumn(column), toChessRow(row));
    }

    public ChessPosition(char chessColumn, int chessRow) {
        super(toBoardMatrixRow(chessRow), toBoardMatrixColumn(chessColumn));

        validateChessPositionCoordinate(chessColumn, chessRow);

        this.chessColumn = chessColumn;
        this.chessRow = chessRow;
    }

    public static ChessPosition buildPositionFor(char otherColumn, int otherRow) {
        return new ChessPosition(otherColumn, otherRow);
    }

    public char getSameChessColumn() {
        return chessColumn;
    }

    public char getNextEastChessColumn() {
        char column = chessColumn;
        return ++column;
    }

    public char getNextWestChessColumn() {
        char column = chessColumn;
        return --column;
    }

    public char getTwoBesideEastChessColumn() {
        char column = chessColumn;
        column += 2;

        return column;
    }

    public char getTwoBesideWestChessColumn() {
        char column = chessColumn;
        column -= 2;

        return column;
    }

    public char getThreeBesideEastChessColumn() {
        char column = chessColumn;
        column += 3;

        return column;
    }

    public char getThreeBesideWestChessColumn() {
        char column = chessColumn;
        column -= 3;

        return column;
    }

    public char getFourBesideWestChessColumn() {
        char column = chessColumn;
        column -= 4;

        return column;
    }

    public int getSameChessRow() {
        return chessRow;
    }

    public int getNextNorthChessRow() {
        return chessRow + 1;
    }

    public int getNextSouthChessRow() {
        return chessRow - 1;
    }

    public int getTwoAheadNorthChessRow() {
        return chessRow + 2;
    }

    public int getTwoBehindSouthChessRow() {
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

    private static int toChessRow(int matrixRow) {
        return CHESS_BOARD_SIZE - matrixRow;
    }

    private static char toChessColumn(int matrixColumn) {
        return (char) ('a' + matrixColumn);
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

    public ChessPosition clonePosition() {
        return new ChessPosition(getChessColumn(), getChessRow());
    }

}
