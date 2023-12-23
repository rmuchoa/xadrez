package chess;

import static chess.ChessPosition.ChessPositionBuilder.builder;

import boardgame.BoardPosition;
import boardgame.BoardPosition.BoardPositionBuilder;

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

    public ChessPosition getNextAbovePosition() {
        return builder()
            .column(getSamePositionColumn())
            .row(getNextAbovePositionRow())
            .build();
    }

    public ChessPosition getNextBelowPosition() {
        return builder()
            .column(getSamePositionColumn())
            .row(getNextBelowPositionRow())
            .build();
    }

    public ChessPosition getNextRightPosition() {
        return builder()
            .column(getNextRightPositionColumn())
            .row(getSamePositionRow())
            .build();
    }

    public ChessPosition getNextLeftPosition() {
        return builder()
            .column(getNextLeftPositionColumn())
            .row(getSamePositionRow())
            .build();
    }

    public ChessPosition getNextDiagonalSuperiorRightPosition() {
        return builder()
            .column(getNextRightPositionColumn())
            .row(getNextAbovePositionRow())
            .build();
    }

    public ChessPosition getNextDiagonalSuperiorLeftPosition() {
        return builder()
            .column(getNextLeftPositionColumn())
            .row(getNextAbovePositionRow())
            .build();
    }

    public ChessPosition getNextDiagonalInferiorRightPosition() {
        return builder()
            .column(getNextRightPositionColumn())
            .row(getNextBelowPositionRow())
            .build();
    }

    public ChessPosition getNextDiagonalInferiorLeftPosition() {
        return builder()
            .column(getNextLeftPositionColumn())
            .row(getNextBelowPositionRow())
            .build();
    }

    private char getSamePositionColumn() {
        return column;
    }

    private char getNextRightPositionColumn() {
        char right = column;
        return ++right;
    }

    private char getNextLeftPositionColumn() {
        char right = column;
        return --right;
    }

    private int getSamePositionRow() {
        return row;
    }

    private int getNextAbovePositionRow() {
        return row + 1;
    }

    private int getNextBelowPositionRow() {
        return row - 1;
    }

    public BoardPosition toBoardPosition() {
        return BoardPositionBuilder.builder()
            .row(toBoardPositionRow())
            .column(toBoardPositionColumn())
            .build();
    }

    private int toBoardPositionRow() {
        return 8 - row;
    }

    private int toBoardPositionColumn() {
        return column - 'a';
    }

    @Override
    public String toString() {
        return "" + column + row;
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

    public static ChessPosition fromPosition(BoardPosition boardPosition) {
        return builder()
            .column(toChessPositionColumn(boardPosition))
            .row(toChessPositionRow(boardPosition))
            .build();
    }

    private static char toChessPositionColumn(BoardPosition boardPosition) {
        return (char) ('a' + boardPosition.getColumn());
    }

    private static int toChessPositionRow(BoardPosition boardPosition) {
        return 8 - boardPosition.getRow();
    }

    public static class ChessPositionBuilder {

        private char column;
        private int row;

        private ChessPositionBuilder() {}

        public static ChessPositionBuilder builder() {
            return new ChessPositionBuilder();
        }

        public ChessPositionBuilder column(char column) {
            this.column = column;
            return this;
        }

        public ChessPositionBuilder row(int row) {
            this.row = row;
            return this;
        }

        public ChessPosition build() {
            return new ChessPosition(column, row);
        }

    }

}
