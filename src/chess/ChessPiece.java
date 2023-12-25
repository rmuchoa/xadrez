package chess;

import boardgame.BoardPiece;
import chess.pieces.King;

public abstract class ChessPiece extends BoardPiece<ChessPosition, ChessPiece, ChessBoard> {

    private final Color color;
    private boolean inCheck;
    private boolean inCheckMate;
    private int moveCount;

    protected ChessPiece(ChessBoard board, Color color) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    protected Color getOpponent() {
        return Color.WHITE.equals(color) ? Color.BLACK : Color.WHITE;
    }

    public boolean isWhitePiece() {
        return Color.WHITE.equals(color);
    }

    public boolean isBlackPiece() {
        return Color.BLACK.equals(color);
    }

    public boolean isInCheck() {
        return inCheck && !inCheckMate;
    }

    public boolean isInCheckMate() {
        return inCheckMate;
    }

    public boolean isKing() {
        return this instanceof King;
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
        ChessPiece chessPiece = getBoardPiecePlacedOn(position);
        return isOpponentFrom(chessPiece);
    }

    protected boolean isOpponentFrom(ChessPiece chessPiece) {
        return chessPiece != null && hasDifferentColorOf(chessPiece.getColor());
    }

    protected boolean hasDifferentColorOf(Color otherColor) {
        return !hasSameColorOf(otherColor);
    }

    protected boolean hasSameColorOf(Color otherColor) {
        return color != null && color.equals(otherColor);
    }

    public void informCheck() {
        inCheck = true;
    }

    public void revokeCheck() {
        inCheck = false;
    }
    
    public void informCheckMate() {
        inCheckMate = true;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }
}
