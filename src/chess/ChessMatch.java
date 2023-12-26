package chess;

import chess.pieces.King;
import java.util.List;

public class ChessMatch {

    private int turn;
    private boolean inCheck;
    private boolean inCheckMate;
    private Color currentPlayer;
    private final ChessBoard board;

    public ChessMatch(ChessBoard board) {
        this.board = board;
        this.currentPlayer = Color.WHITE;
        this.turn = 1;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isInCheck() {
        return inCheck && !inCheckMate;
    }

    public boolean isInCheckMate() {
        return inCheckMate;
    }

    public boolean isNotInCheckMate() {
        return !isInCheckMate();
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public void placeNewPiece(char column, int row, ChessPiece piece) {
        ChessPosition position = ChessPosition.builder()
            .chessColumn(column)
            .chessRow(row)
            .build();

        board.placePieceOn(position, piece);
    }

    public List<List<ChessPiece>> getPieces() {
        return board.getAllPieces();
    }

    public List<ChessPosition> getAllAvailableTargetFor(ChessPosition sourcePosition) {
        ChessPiece piece = board.getPiecePlacedOn(sourcePosition);
        validateMovementOriginFrom(sourcePosition);
        
        return piece.getAllAvailableTargetPositions();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        validateMovementOriginFrom(sourcePosition);
        validateTargetPositionAvailability(sourcePosition, targetPosition);

        ChessPiece capturedPiece = makeMove(sourcePosition, targetPosition);
        testForCheckMovement(sourcePosition, targetPosition, capturedPiece);

        if (!inCheckMate)
            nextTurn();

        return capturedPiece;
    }

    public ChessPiece makeMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessPiece movingPiece = board.removePieceFrom(sourcePosition);
        ChessPiece capturedPiece = board.removePieceFrom(targetPosition);

        board.placePieceOn(targetPosition, movingPiece);
        movingPiece.increaseMoveCount();

        applyRookCastling(movingPiece, sourcePosition, targetPosition);

        return capturedPiece;
    }

    private void applyRookCastling(ChessPiece movingPiece, ChessPosition sourcePosition, ChessPosition targetPosition) {
        if (movingPiece instanceof King && targetPosition.getChessColumn() == sourcePosition.getChessColumn() + 2) {
            ChessPosition towerPosition = movingPiece.getPosition().getNextBesideEastCastlingMovementPosition();
            ChessPosition towerTargetPosition = movingPiece.getPosition().getNextBesideWestCastlingMovementPosition();
            ChessPiece rook = board.removePieceFrom(towerPosition);
            board.placePieceOn(towerTargetPosition, rook);
            rook.increaseMoveCount();
            return;
        }

        if (movingPiece instanceof King && targetPosition.getChessColumn() == sourcePosition.getChessColumn() - 2) {
            ChessPosition towerPosition = movingPiece.getPosition().getTwoBesideWestCastlingMovementPosition();
            ChessPosition towerTargetPosition = movingPiece.getPosition().getNextBesideEastCastlingMovementPosition();
            ChessPiece rook = board.removePieceFrom(towerPosition);
            board.placePieceOn(towerTargetPosition, rook);
            rook.increaseMoveCount();
        }
    }

    public void undoMove(ChessPosition sourcePosition, ChessPosition targetPosition, ChessPiece capturedPiece) {
        ChessPiece movingPiece = board.removePieceFrom(targetPosition);
        board.placePieceOn(sourcePosition, movingPiece);
        movingPiece.decreaseMoveCount();

        if (capturedPiece != null)
            board.placePieceOn(targetPosition, capturedPiece);

        unapplyRookCastling(movingPiece, sourcePosition, targetPosition);
    }

    private void unapplyRookCastling(ChessPiece movingPiece, ChessPosition sourcePosition, ChessPosition targetPosition) {
        if (movingPiece instanceof King && targetPosition.getChessColumn() == sourcePosition.getChessColumn() + 2) {
            ChessPosition towerPosition = movingPiece.getPosition().getNextWestPosition();
            ChessPosition towerTargetPosition = movingPiece.getPosition().getThreeBesideEastCastlingMovementPosition();
            ChessPiece rook = board.removePieceFrom(towerPosition);
            board.placePieceOn(towerTargetPosition, rook);
            rook.increaseMoveCount();
            return;
        }

        if (movingPiece instanceof King && targetPosition.getChessColumn() == sourcePosition.getChessColumn() - 2) {
            ChessPosition towerPosition = movingPiece.getPosition().getNextWestPosition();
            ChessPosition towerTargetPosition = movingPiece.getPosition().getFourBesideWestCastlingMovementPosition();
            ChessPiece rook = board.removePieceFrom(towerPosition);
            board.placePieceOn(towerTargetPosition, rook);
            rook.increaseMoveCount();
        }
    }

    private void testForCheckMovement(ChessPosition sourcePosition, ChessPosition targetPosition, ChessPiece capturedPiece) {
        try {
            testForOwnKingInCheck();
        } catch (ChessException ex) {
            undoMove(sourcePosition, targetPosition, capturedPiece);
            throw ex;
        }

        testForOpponentKingInCheck();
        testForOpponentKingInCheckMate();
    }

    private void testForOwnKingInCheck() {
        King ownKing = board.getKingOf(currentPlayer);

        if (canDetectCheckScenario(ownKing))
            throw new ChessException("This movement put your own king in check. Undoing all movement!");
        else
            ownKing.revokeCheck();
    }

    private void testForOpponentKingInCheck() {
        King opponentKing = board.getOpponentKingFrom(currentPlayer);

        inCheck = canDetectCheckScenario(opponentKing);

        if (inCheck)
            opponentKing.informCheck();
    }

    private void testForOpponentKingInCheckMate() {
        if (!inCheck)
            return;

        King opponentKing = board.getOpponentKingFrom(currentPlayer);
        inCheckMate = board.getAllPlacedPiecesOf(opponentKing.getColor())
            .stream()
            .noneMatch(piece -> piece.canSaveKingFromCheck(opponentKing));

        if (inCheckMate)
            opponentKing.informCheckMate();
    }

    public boolean cannotDetectCheckScenario(King king) {
        return !canDetectCheckScenario(king);
    }

    private boolean canDetectCheckScenario(King king) {
        return board.getAllPlacedPiecesOf(king.getOpponent())
            .stream()
            .anyMatch(piece -> piece.canTargetThis(king.getPosition()));
    }

    private void nextTurn() {
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void validateMovementOriginFrom(ChessPosition sourcePosition) {
        validatePiecePresenceOn(sourcePosition);
        validateCurrentPlayerPiece(sourcePosition);
        validatePieceMobility(sourcePosition);
    }

    private void validatePieceMobility(ChessPosition sourcePosition) {
        ChessPiece piece = board.getPiecePlacedOn(sourcePosition);

        if (piece.hasNoAvailableMovements())
            throw new ChessException("There is no possible movements for the chosen " + piece + " piece");
    }

    private void validateCurrentPlayerPiece(ChessPosition sourcePosition) {
        ChessPiece piece = board.getPiecePlacedOn(sourcePosition);

        if (piece.hasDifferentColorOf(currentPlayer))
            throw new ChessException("The chosen piece "+piece+" on "+sourcePosition+" is not yours");
    }

    private void validatePiecePresenceOn(ChessPosition sourcePosition) {
        if (board.isBoardPositionEmpty(sourcePosition))
            throw new ChessException("There is no piece present on source position " + sourcePosition);
    }

    private void validateTargetPositionAvailability(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessPiece piece = board.getPiecePlacedOn(sourcePosition);

        if (piece.canNotTargetThis(targetPosition))
            throw new ChessException("The piece cannot move to target position " + targetPosition);
    }

}
