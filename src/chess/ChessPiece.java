package chess;

import boardgame.Board;
import boardgame.BoardPiece;
import boardgame.BoardPosition;

public abstract class ChessPiece extends BoardPiece {

    private final Color color;

    public ChessPiece(Board board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected boolean isAllowedToTarget(ChessPosition position) {
        if (doesNotExistsOnBoard(position))
            return false;

        if (thereIsNoPiecePlacedOn(position))
            return true;

        return thereIsAnOpponentPiecePlacedOn(position);
    }

    protected boolean doesNotExistsOnBoard(ChessPosition position) {
        return doesNotExistsOnBoard(position.toBoardPosition());
    }

    protected boolean thereIsNoPiecePlacedOn(ChessPosition position) {
        return thereIsNoPiecePlacedOn(position.toBoardPosition());
    }

    protected boolean thereIsNoPiecePlacedOn(BoardPosition boardPosition) {
        return getBoardPiecePlacedOn(boardPosition) == null;
    }

    protected boolean thereIsAnOpponentPiecePlacedOn(ChessPosition position) {
        return thereIsAnOpponentPiecePlacedOn(position.toBoardPosition());
    }

    protected boolean thereIsAnOpponentPiecePlacedOn(BoardPosition boardPosition) {
        ChessPiece chessPiece = (ChessPiece) getBoardPiecePlacedOn(boardPosition);
        return isOpponentFrom(chessPiece);
    }

    private boolean isOpponentFrom(ChessPiece chessPiece) {
        return chessPiece != null && hasDifferentColorOf(chessPiece.getColor());
    }

    private boolean hasDifferentColorOf(Color otherColor) {
        return !hasSameColorOf(otherColor);
    }

    private boolean hasSameColorOf(Color otherColor) {
        return color != null && color.equals(otherColor);
    }

}
