package boardgame;

public class Board {

    private final int totalRows;
    private final int totalColumns;
    private final BoardPiece[][] boardPieces;

    public Board(int totalRows, int totalColumns) {
        validateBoardSizing(totalRows, totalColumns);

        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.boardPieces = new BoardPiece[totalRows][totalColumns];
    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public BoardPiece getPiecePlacedOn(BoardPosition position) {
        return getPiecePlacedOn(position.getMatrixRow(), position.getMatrixColumn());
    }

    public BoardPiece getPiecePlacedOn(int row, int column) {
        validatePositionExistence(row, column);

        return boardPieces[row][column];
    }

    public void placePieceOn(BoardPosition position, BoardPiece boardPiece) {
        validatePositionAvailability(position);

        boardPieces[position.getMatrixRow()][position.getMatrixColumn()] = boardPiece;
        boardPiece.placeOnPosition(position);
    }

    public BoardPiece removePieceFrom(BoardPosition position) {
        validatePositionExistence(position.getMatrixRow(), position.getMatrixColumn());

        if (isBoardPositionEmpty(position))
            return null;

        BoardPiece boardPiece = getPiecePlacedOn(position);
        boardPieces[position.getMatrixRow()][position.getMatrixColumn()] = null;
        boardPiece.takeOutOfPosition();
        return boardPiece;
    }

    public boolean doesExists(BoardPosition position) {
        return doesExistsPosition(position.getMatrixRow(), position.getMatrixColumn());
    }

    public boolean doesNotExists(BoardPosition position) {
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

    public boolean isBoardPositionOccupied(BoardPosition position) {
        return !isBoardPositionEmpty(position);
    }

    public boolean isBoardPositionEmpty(BoardPosition boardPosition) {
        validatePositionExistence(boardPosition.getMatrixRow(), boardPosition.getMatrixColumn());

        return getPiecePlacedOn(boardPosition) == null;
    }

    private void validatePositionAvailability(BoardPosition position) {
        if (isBoardPositionOccupied(position))
            throw new BoardException("There is already a piece on position " + position);
    }

    private void validatePositionExistence(int row, int column) {
        if (doesNotExistsPosition(row, column))
            throw new BoardException("Position not on the board. row["+row+"], column["+column+"]");
    }

    private static void validateBoardSizing(int rows, int columns) {
        if (rows < 1 || columns < 1)
            throw new BoardException("Error creating board with "+rows+" rows and "+columns+": there must be at least 1 row and 1 column.");
    }

}
