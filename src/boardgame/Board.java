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

    public BoardPiece getBoardPiecePlacedOn(BoardPosition position) {
        return getBoardPiecePlacedOn(position.getRow(), position.getColumn());
    }

    public BoardPiece getBoardPiecePlacedOn(int row, int column) {
        validatePositionExistence(row, column);

        return boardPieces[row][column];
    }

    public void placePieceOn(BoardPosition position, BoardPiece boardPiece) {
        validatePositionAvailability(position);

        boardPieces[position.getRow()][position.getColumn()] = boardPiece;
        boardPiece.placeOnPosition(position);
    }

    public BoardPiece removePieceFrom(BoardPosition position) {
        validatePositionExistence(position.getRow(), position.getColumn());

        if (isBoardPositionEmpty(position))
            return null;

        BoardPiece boardPiece = getBoardPiecePlacedOn(position);
        boardPieces[position.getRow()][position.getColumn()] = null;
        boardPiece.takeOutOfPosition();
        return boardPiece;
    }

    public boolean positionExists(BoardPosition position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    private boolean positionDoesNotExists(int row, int column) {
        return !positionExists(row, column);
    }

    private boolean positionExists(int row, int column) {
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

    private void validatePositionAvailability(BoardPosition position) {
        if (isBoardPositionOccupied(position))
            throw new BoardException("There is already a piece on position " + position);
    }

    public boolean isBoardPositionOccupied(BoardPosition position) {
        return !isBoardPositionEmpty(position);
    }

    public boolean isBoardPositionEmpty(BoardPosition boardPosition) {
        validatePositionExistence(boardPosition.getRow(), boardPosition.getColumn());

        return getBoardPiecePlacedOn(boardPosition) == null;
    }

    private void validatePositionExistence(int row, int column) {
        if (positionDoesNotExists(row, column))
            throw new BoardException("Position not on the board. row["+row+"], column["+column+"]");
    }

    private static void validateBoardSizing(int rows, int columns) {
        if (rows < 1 || columns < 1)
            throw new BoardException("Error creating board with "+rows+" rows and "+columns+": there must be at least 1 row and 1 column.");
    }

}
