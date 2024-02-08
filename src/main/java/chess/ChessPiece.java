package chess;

import board.BoardPiece;
import chess.pieces.King;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class ChessPiece extends BoardPiece<ChessPosition, ChessPiece, ChessBoard> {

    private final Color color;
    private ChessMatch match;
    protected int moveCount;
    protected List<ChessMovement> availableMovements;

    protected ChessPiece(Color color) {
        super();
        this.color = color;
        this.availableMovements = new ArrayList<>();
    }

    protected void applyMatch() {
        this.match = getBoard().getMatch();
    }

    public void clearAvailableMovements() {
        availableMovements.clear();
    }

    public void applyAvailableMovements(List<ChessMovement> availableMovements) {
        this.availableMovements.addAll(availableMovements);
    }

    public boolean isWhitePiece() {
        return Color.WHITE.equals(color);
    }

    public boolean isBlackPiece() {
        return Color.BLACK.equals(color);
    }

    public boolean isKing() {
        return this instanceof King;
    }

    public boolean isOpponentOf(ChessPiece chessPiece) {
        return chessPiece != null && hasDifferentColorOf(chessPiece.getColor());
    }

    public boolean isCompanionOf(ChessPiece chessPiece) {
        return chessPiece != null && hasSameColorOf(chessPiece.getColor());
    }

    protected boolean hasDifferentColorOf(Color otherColor) {
        return !hasSameColorOf(otherColor);
    }

    protected boolean hasSameColorOf(Color otherColor) {
        return color.equals(otherColor);
    }

    public boolean isFromOpponentPlayer() {
        return !isFromCurrentPlayer();
    }

    public boolean isFromCurrentPlayer() {
        return color.equals(match.getCurrentPlayer());
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public boolean hasAlreadyMoved() {
        return moveCount > 0;
    }

    public boolean hasNotMovedYet() {
        return moveCount == 0;
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof ChessPiece
            && color.equals(((ChessPiece) any).getColor())
            && super.equals(any);
    }

    public void setUpAvailableMovements() {
        clearAvailableMovements();
        applyAllAvailableMovements();
    }

    public abstract void applyAllAvailableMovements();

    public boolean canBeTargetedBy(ChessPiece piece) {
        return piece.canTargetThis(getPosition());
    }

    public boolean canNotTargetThis(ChessPosition position) {
        return !canTargetThis(position);
    }

    public boolean canTargetThis(ChessPosition position) {
        return getAvailableMovements().stream()
            .map(ChessMovement::getTarget)
            .anyMatch(position::equals);
    }

    public boolean hasNoAvailableMovements() {
        return getAvailableMovements().isEmpty();
    }

    public abstract ChessPiece clonePiece(ChessBoard clonedBoard);

    public boolean isInCheckPiece() {
        return this instanceof King king && king.isInCheck();
    }

    public boolean isInCheckMatePiece() {
        return this instanceof King king && king.isInCheckMate();
    }

}
