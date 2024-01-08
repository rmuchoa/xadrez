package chess;

import chess.movement.CastlingMovement;
import chess.pieces.King;
import java.util.List;
import lombok.Getter;

public class ChessMatch {

    @Getter
    private int turn;
    private boolean inCheck;
    @Getter
    private boolean inCheckMate;
    @Getter
    private Color currentPlayer;
    @Getter
    private final ChessBoard board;

    public ChessMatch() {
        this.board = new ChessBoard(this);
        this.currentPlayer = Color.WHITE;
        this.turn = 1;
    }

    public boolean isInCheck() {
        return inCheck && !inCheckMate;
    }

    public boolean isNotInCheckMate() {
        return !isInCheckMate();
    }

    public void placeNewPiece(char column, int row, ChessPiece piece) {
        ChessPosition position = new ChessPosition(column, row);

        board.placePieceOn(position, piece);
    }

    public List<List<ChessPiece>> getPieces() {
        return board.getAllPieces();
    }

    public void setUpAvailableMovements() {
        board.getAllPlacedPieces()
            .forEach(ChessPiece::setUpAvailableMovements);
    }

    public List<ChessMovement> getAvailableMovementsFor(ChessPosition sourcePosition) {
        ChessPiece piece = board.getPiecePlacedOn(sourcePosition);
        board.validateMovementOriginFrom(sourcePosition);
        
        return piece.getAvailableMovements();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessMovement movement = board.getMovementFor(sourcePosition, targetPosition);

        ChessPiece capturedPiece = makeMovement(movement);
        testForCheckMovement(movement, capturedPiece);

        if (!inCheckMate)
            nextTurn();

        return capturedPiece;
    }

    public ChessPiece makeMovement(ChessMovement movement) {
        ChessPiece movingPiece = board.removePieceFrom(movement.getSource());
        ChessPiece capturedPiece = board.removePieceFrom(movement.getTarget());

        board.placePieceOn(movement.getTarget(), movingPiece);
        movingPiece.increaseMoveCount();

        applyRookCastling(movingPiece, movement);

        board.getAllPlacedPieces()
            .forEach(ChessPiece::setUpAvailableMovements);

        return capturedPiece;
    }

    private void applyRookCastling(ChessPiece movingPiece, ChessMovement movement) {
        if (movingPiece instanceof King && movement instanceof CastlingMovement kingMovement) {

            ChessMovement rookMovement = kingMovement.getRookMovement();

            ChessPiece rook = board.removePieceFrom(rookMovement.getSource());
            board.placePieceOn(rookMovement.getTarget(), rook);
            rook.increaseMoveCount();
        }
    }

    public void undoMovement(ChessMovement movement, ChessPiece capturedPiece) {
        ChessPiece movingPiece = board.removePieceFrom(movement.getTarget());
        board.placePieceOn(movement.getSource(), movingPiece);
        movingPiece.decreaseMoveCount();

        if (capturedPiece != null)
            board.placePieceOn(movement.getTarget(), capturedPiece);

        unapplyRookCastling(movingPiece, movement);

        board.getAllPlacedPieces()
            .forEach(ChessPiece::setUpAvailableMovements);
    }

    private void unapplyRookCastling(ChessPiece movingPiece, ChessMovement movement) {
        if (movingPiece instanceof King && movement instanceof CastlingMovement kingMovement) {

            ChessMovement rookMovement = kingMovement.getRookMovement();

            ChessPiece rook = board.removePieceFrom(rookMovement.getTarget());
            board.placePieceOn(rookMovement.getSource(), rook);
            rook.increaseMoveCount();
        }
    }

    private void testForCheckMovement(ChessMovement movement, ChessPiece capturedPiece) {
        try {
            testForOwnKingInCheck();
        } catch (ChessException ex) {
            undoMovement(movement, capturedPiece);
            throw ex;
        }

        testForOpponentKingInCheck();
        testForOpponentKingInCheckMate();
    }

    private void testForOwnKingInCheck() {
        King ownKing = getCurrentKing();

        if (canDetectCheckScenario(ownKing))
            throw new ChessException("This movement put your own king in check. Undoing all movement!");
        else
            ownKing.revokeCheck();
    }

    private void testForOpponentKingInCheck() {
        King opponentKing = getOpponentKing();

        inCheck = canDetectCheckScenario(opponentKing);

        if (inCheck)
            opponentKing.informCheck();
    }

    private void testForOpponentKingInCheckMate() {
        if (!inCheck)
            return;

        King opponentKing = getOpponentKing();

        inCheckMate = board.getAllPlacedPieces()
            .stream()
            .filter(opponentKing::isCompanionOf)
            .noneMatch(opponentKing::canBeSavedDuringCheckBy);

        if (inCheckMate)
            opponentKing.informCheckMate();
    }

    public boolean cannotDetectCheckScenario(King king) {
        return !canDetectCheckScenario(king);
    }

    private boolean canDetectCheckScenario(King king) {
        return board.getAllPlacedPieces()
            .stream()
            .filter(king::isOpponentOf)
            .anyMatch(king::canBeTargetedBy);
    }

    public King getOpponentKing() {
        return board.getAllPlacedPieces()
            .stream()
            .filter(ChessPiece::isKing)
            .map(piece -> (King) piece)
            .filter(King::isFromOpponentPlayer)
            .findAny()
            .orElse(null);
    }

    public King getCurrentKing() {
        return board.getAllPlacedPieces()
            .stream()
            .filter(ChessPiece::isKing)
            .map(piece -> (King) piece)
            .filter(King::isFromCurrentPlayer)
            .findAny()
            .orElse(null);
    }

    private void nextTurn() {
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public ChessMatch cloneMatch() {
        ChessMatch clonedMatch = new ChessMatch();
        clonedMatch.turn = getTurn();
        clonedMatch.inCheck = isInCheck();
        clonedMatch.inCheckMate = isInCheckMate();
        clonedMatch.currentPlayer = getCurrentPlayer();
        getBoard().cloneIntoBoard(clonedMatch.getBoard());
        return clonedMatch;
    }
}
