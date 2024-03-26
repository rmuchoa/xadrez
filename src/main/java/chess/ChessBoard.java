package chess;

import board.Board;
import chess.pieces.King;
import lombok.Getter;

@Getter
public class ChessBoard extends Board<ChessPosition, ChessPiece, ChessBoard> {

    private final ChessMatch match;

    public static final int CHESS_BOARD_SIZE = 8;

    public ChessBoard(ChessMatch match) {
        super(CHESS_BOARD_SIZE, CHESS_BOARD_SIZE);
        this.match = match;
    }

    public ChessMovement getMovementFor(ChessPosition source, ChessPosition target) {
        validateMobilityFromOrigin(source);
        validateTargetPositionAvailability(source, target);

        ChessPiece movingPiece = getPiecePlacedOn(source);

        return movingPiece.getMovementFor(target);
    }

    public void validateMobilityFor(ChessPiece piece) {
        validateMobilityFromOrigin(piece.getPosition());
    }

    public void validateMobilityFromOrigin(ChessPosition source) {
        validatePiecePresenceOn(source);
        validateCurrentPlayerPiece(source);
        validatePieceMobility(source);
    }

    public void validatePiecePresenceOn(ChessPosition source) {
        if (isBoardPositionEmpty(source))
            throw new ChessException("There is no piece present on source position " + source);
    }

    public void validateCurrentPlayerPiece(ChessPosition source) {
        ChessPiece piece = getPiecePlacedOn(source);

        if (piece.isFromOpponentPlayer())
            throw new ChessException("The chosen piece "+piece+" on "+source+" is not yours");
    }

    public void validatePieceMobility(ChessPosition source) {
        ChessPiece piece = getPiecePlacedOn(source);

        if (piece.hasNoAvailableMovements())
            throw new ChessException("There is no possible movements for the chosen " + piece + " piece");
    }

    private void validateTargetPositionAvailability(ChessPosition source, ChessPosition target) {
        ChessPiece piece = getPiecePlacedOn(source);

        if (piece.canNotTargetThis(target))
            throw new ChessException("The piece cannot move to target position " + target);
    }

    public boolean canDetectCheckFor(King king) {
        return getAllPlacedPieces()
            .stream()
            .filter(king::isOpponentOf)
            .anyMatch(king::canBeTargetedBy);
    }

    public King getOpponentKing() {
        return getAllPlacedPieces()
            .stream()
            .filter(ChessPiece::isKing)
            .map(piece -> (King) piece)
            .filter(King::isFromOpponentPlayer)
            .findAny()
            .orElse(null);
    }

    public King getCurrentKing() {
        return getAllPlacedPieces()
            .stream()
            .filter(ChessPiece::isKing)
            .map(piece -> (King) piece)
            .filter(King::isFromCurrentPlayer)
            .findAny()
            .orElse(null);
    }

    public void checkOwnKing() {
        King ownKing = getCurrentKing();

        if (canDetectCheckFor(ownKing))
            throw new ChessException("This movement put your own king in check. Undoing all movement!");
        else
            ownKing.revokeCheck();
    }

    public boolean isOpponentKingNotInCheck() {
        return !isOpponentKingInCheck();
    }

    public boolean isOpponentKingInCheck() {
        King opponentKing = getOpponentKing();

        if (canDetectCheckFor(opponentKing)) {
            opponentKing.informCheck();
            return true;
        }
        return false;
    }

    public boolean isOpponentKingInCheckMate() {
        if (isOpponentKingNotInCheck())
            return false;

        King opponentKing = getOpponentKing();

        if (hasNoCheckSalvation(opponentKing)) {
            opponentKing.informCheckMate();
            return true;
        }

        return false;
    }

    public boolean hasNoCheckSalvation(King king) {
        return getAllPlacedPieces()
            .stream()
            .filter(king::isCompanionOf)
            .noneMatch(king::canBeSavedDuringCheckBy);
    }

    public void cloneIntoBoard(ChessBoard clonedBoard) {
        for (int row = 0; row < CHESS_BOARD_SIZE; row++) {
            for (int column = 0; column < CHESS_BOARD_SIZE; column++) {
                ChessPiece piece = getAllPieces().get(row).get(column);
                if (piece != null) {
                    ChessPiece clonedPiece = piece.clonePiece(clonedBoard);
                    clonedBoard.getAllPieces().get(row).set(column, clonedPiece);
                }
            }
        }
    }

}
