package chess;

import boardgame.Board;
import boardgame.BoardPiece;

public abstract class ChessPiece extends BoardPiece<ChessPosition> {

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

        return thereIsAnOpponentPlacedOn(position);
    }

    protected boolean thereIsNoPiecePlacedOn(ChessPosition position) {
        return getBoardPiecePlacedOn(position) == null;
    }

    protected boolean thereIsAnOpponentPlacedOn(ChessPosition position) {
        ChessPiece chessPiece = (ChessPiece) getBoardPiecePlacedOn(position);
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
