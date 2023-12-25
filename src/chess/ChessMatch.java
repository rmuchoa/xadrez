package chess;

import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

    public static final int CHESS_BOARD_SIZE = 8;

    private int turn;
    private boolean inCheck;
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
        return inCheck;
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

        nextTurn();

        return capturedPiece;
    }

    private ChessPiece makeMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessPiece movingPiece = board.removePieceFrom(sourcePosition);
        ChessPiece capturedPiece = board.removePieceFrom(targetPosition);

        board.placePieceOn(targetPosition, movingPiece);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        return capturedPiece;
    }

    private void testForCheckMovement(ChessPosition sourcePosition, ChessPosition targetPosition, ChessPiece capturedPiece) {
        try {
            testForOwnKingInCheck();
        } catch (ChessException ex) {
            undoMove(sourcePosition, targetPosition, capturedPiece);
            throw ex;
        }

        testForOpponentKingInCheck();
    }

    private void undoMove(ChessPosition sourcePosition, ChessPosition targetPosition, ChessPiece capturedPiece) {
        ChessPiece movingPiece = board.removePieceFrom(targetPosition);
        board.placePieceOn(sourcePosition, movingPiece);

        if (capturedPiece != null) {
            board.placePieceOn(targetPosition, capturedPiece);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void testForOwnKingInCheck() {
        ChessPiece ownKing = board.getKingOf(currentPlayer);

        List<ChessPiece> allOpponentPieces = board.getAllPlacedPiecesOf(ownKing.getOpponent());

        boolean ownKingInCheck = allOpponentPieces.stream()
            .anyMatch(piece -> piece.canTargetThis(ownKing.getPosition()));

        if (ownKingInCheck)
            throw new ChessException("This movement put your own king in check. Undoing all movement!");
        else
            ownKing.revokeCheck();
    }

    private void testForOpponentKingInCheck() {
        ChessPiece opponentKing = board.getOpponentKingFrom(currentPlayer);

        inCheck = board.getAllPlacedPiecesOf(currentPlayer)
            .stream()
            .anyMatch(piece -> piece.canTargetThis(opponentKing.getPosition()));

        if (inCheck)
            opponentKing.informCheck();
    }

    private void nextTurn() {
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void initialSetup() {
        placeNewPiece('c', 1, Rook.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('c', 2, Rook.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('d', 2, Rook.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('e', 2, Rook.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('e', 1, Rook.builder().board(board).color(Color.WHITE).build());
        placeNewPiece('d', 1, King.builder().board(board).color(Color.WHITE).build());

        placeNewPiece('c', 7, Rook.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('c', 8, Rook.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('d', 7, Rook.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('e', 7, Rook.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('e', 8, Rook.builder().board(board).color(Color.BLACK).build());
        placeNewPiece('d', 8, King.builder().board(board).color(Color.BLACK).build());
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
