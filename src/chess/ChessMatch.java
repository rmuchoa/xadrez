package chess;

import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    public static final int CHESS_BOARD_SIZE = 8;

    private int turn;
    private boolean inCheck;
    private boolean inCheckMate;
    private Color currentPlayer;
    private final List<ChessPiece> piecesOnTheBoard;
    private final List<ChessPiece> capturedPieces;
    private final ChessBoard board;

    public ChessMatch() {
        board = ChessBoard.builder()
            .totalRows(CHESS_BOARD_SIZE)
            .totalColumns(CHESS_BOARD_SIZE)
            .build();

        piecesOnTheBoard = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        currentPlayer = Color.WHITE;
        turn = 1;
        initialSetup();
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

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        ChessPosition position = ChessPosition.builder()
            .chessColumn(column)
            .chessRow(row)
            .build();

        board.placePieceOn(position, piece);
        piecesOnTheBoard.add(piece);
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

    private ChessPiece makeMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessPiece movingPiece = board.removePieceFrom(sourcePosition);
        ChessPiece capturedPiece = board.removePieceFrom(targetPosition);

        board.placePieceOn(targetPosition, movingPiece);
        movingPiece.increaseMoveCount();

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(ChessPosition sourcePosition, ChessPosition targetPosition, ChessPiece capturedPiece) {
        ChessPiece movingPiece = board.removePieceFrom(targetPosition);
        board.placePieceOn(sourcePosition, movingPiece);
        movingPiece.decreaseMoveCount();

        if (capturedPiece != null) {
            board.placePieceOn(targetPosition, capturedPiece);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
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
            .noneMatch(opponentPiece -> pieceCanSaveKingFromCheck(opponentPiece, opponentKing));

        if (inCheckMate)
            opponentKing.informCheckMate();
    }

    private boolean pieceCanSaveKingFromCheck(ChessPiece piece, King king) {
        return piece.getAllAvailableTargetPositions()
            .stream()
            .anyMatch(targetPosition -> {
                boolean canSaveKing = false;
                ChessPosition sourcePosition = piece.getPosition();

                ChessPiece captured = makeMove(sourcePosition, targetPosition);

                if (cannotDetectCheckScenario(king))
                    canSaveKing = true;

                undoMove(sourcePosition, targetPosition, captured);

                return canSaveKing;
            });
    }

    private boolean cannotDetectCheckScenario(King king) {
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

    private void initialSetup() {
        placeNewPiece('a', 1, Rook.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('d', 1, King.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('h', 1, Rook.builder().board(board).color(Color.WHITE).build());

        placeNewPiece('a', 2, Pawn.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('b', 2, Pawn.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('c', 2, Pawn.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('d', 2, Pawn.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('e', 2, Pawn.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('f', 2, Pawn.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('g', 2, Pawn.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('h', 2, Pawn.builder().board(board).color(Color.WHITE).build());

        placeNewPiece('a', 8, Rook.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('d', 8, King.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('h', 8, Rook.builder().board(board).color(Color.BLACK).build());

        placeNewPiece('a', 7, Pawn.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('b', 7, Pawn.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('c', 7, Pawn.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('d', 7, Pawn.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('e', 7, Pawn.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('f', 7, Pawn.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('g', 7, Pawn.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('h', 7, Pawn.builder().board(board).color(Color.BLACK).build());

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
