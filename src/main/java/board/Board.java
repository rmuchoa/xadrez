package board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board<T extends BoardPosition, P extends BoardPiece<T, P, B>, B extends Board<T, P, B>> {

    private final int totalRows;
    private final int totalColumns;
    private final List<List<P>> boardPieces;

    protected Board(int totalRows, int totalColumns) {
        validateBoardSizing(totalRows, totalColumns);

        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.boardPieces = buildBidimensionalList(totalRows, totalColumns);
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public List<List<P>> getAllPieces() {
        return boardPieces;
    }

    protected List<P> getAllPlacedPieces() {
        return boardPieces.stream()
            .reduce(new ArrayList<>(), (accumulator, nullable) -> {
                List<P> nonNullable = new ArrayList<>(nullable);
                nonNullable.removeAll(Collections.singleton(null));
                accumulator.addAll(nonNullable);
                return accumulator;
            });
    }

    public P getPiecePlacedOn(T position) {
        return getPiecePlacedOn(position.getMatrixRow(), position.getMatrixColumn());
    }

    public P getPiecePlacedOn(int row, int column) {
        validatePositionExistence(row, column);

        return boardPieces.get(row).get(column);
    }

    public void placePieceOn(T position, P piece) {
        validatePositionAvailability(position);

        boardPieces.get(position.getMatrixRow())
            .set(position.getMatrixColumn(), piece);
        piece.placeOnPosition(position);
    }

    public P removePieceFrom(T position) {
        if (isBoardPositionEmpty(position))
            return null;

        P piece = getPiecePlacedOn(position);
        boardPieces.get(position.getMatrixRow())
            .set(position.getMatrixColumn(), null);
        piece.takeOutOfPosition();
        return piece;
    }

    public boolean doesExists(T position) {
        return doesExistsPosition(position.getMatrixRow(), position.getMatrixColumn());
    }

    public boolean doesNotExists(T position) {
        return doesNotExistsPosition(position.getMatrixRow(), position.getMatrixColumn());
    }

    private boolean doesNotExistsPosition(int row, int column) {
        return !doesExistsPosition(row, column);
    }

    private boolean doesExistsPosition(int row, int column) {
        return rowExists(row) &&
            columnExists(column);
    }

    private boolean rowExists(int row) {
        return isRowGreaterThanZero(row) &&
            isRowLowerThanTotalRows(row);
    }

    private boolean columnExists(int column) {
        return isColumnGreaterThanZero(column) &&
            isColumnLowerThanTotalColumns(column);
    }

    private static boolean isColumnGreaterThanZero(int column) {
        return column >= 0;
    }

    private boolean isColumnLowerThanTotalColumns(int column) {
        return column < totalColumns;
    }

    private static boolean isRowGreaterThanZero(int row) {
        return row >= 0;
    }

    private boolean isRowLowerThanTotalRows(int row) {
        return row < totalRows;
    }

    public boolean isBoardPositionOccupied(T position) {
        return !isBoardPositionEmpty(position);
    }

    public boolean isBoardPositionEmpty(T position) {
        validatePositionExistence(position.getMatrixRow(), position.getMatrixColumn());

        return getPiecePlacedOn(position) == null;
    }

    private void validatePositionAvailability(T position) {
        if (isBoardPositionOccupied(position))
            throw new BoardException("There is already a piece on position " + position);
    }

    private void validatePositionExistence(int row, int column) {
        if (doesNotExistsPosition(row, column))
            throw new BoardException("Position not on the board. row["+row+"], column["+column+"]");
    }

    private static void validateBoardSizing(int rows, int columns) {
        if (rows < 1 || columns < 1)
            throw new BoardException("Error creating board with "+rows+" rows and "+columns+" columns: there must be at least 1 row and 1 column.");
    }

    private List<List<P>> buildBidimensionalList(int totalRows, int totalColumns) {
        List<List<P>> bidimensionalList = new ArrayList<>(totalRows);

        for (int i=0; i<totalRows; i++) {
            List<P> line = new ArrayList<>(totalColumns);
            bidimensionalList.add(line);

            for (int j=0; j<totalColumns; j++)
                line.add(null);
        }

        return bidimensionalList;
    }

}
