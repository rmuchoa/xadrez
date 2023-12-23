package chess;

import boardgame.Board;

import chess.pieces.King;
import chess.pieces.Rook;
import java.util.List;

public class ChessMatch {

    public static final int CHESS_BOARD_SIZE = 8;

    private final Board board;

    public ChessMatch() {
        board = new Board(CHESS_BOARD_SIZE,CHESS_BOARD_SIZE);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] chessPieces = new ChessPiece[CHESS_BOARD_SIZE][CHESS_BOARD_SIZE];

        for (int row=0; row < board.getTotalRows(); row++) {
            for (int column=0; column < board.getTotalColumns(); column++) {
                chessPieces[row][column] = (ChessPiece) board.getPiecePlacedOn(row, column);
            }
        }

        return chessPieces;
    }

    public List<ChessPosition> getAllAvailableTargetFor(ChessPosition sourcePosition) {
        ChessPiece piece = (ChessPiece) board.getPiecePlacedOn(sourcePosition);
        validateMovementOriginFrom(sourcePosition);
        
        return piece.getAllAvailableTargetPositions();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        validateMovementOriginFrom(sourcePosition);
        validateTargetPositionAvailability(sourcePosition, targetPosition);

        return makeMove(sourcePosition, targetPosition);
    }

    private ChessPiece makeMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessPiece movingPiece = (ChessPiece) board.removePieceFrom(sourcePosition);
        ChessPiece capturedPiece = (ChessPiece) board.removePieceFrom(targetPosition);

        board.placePieceOn(targetPosition, movingPiece);

        return capturedPiece;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        ChessPosition position = ChessPosition.builder().chessColumn(column).chessRow(row).build();
        board.placePieceOn(position, piece);
    }

    private void initialSetup() {
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }

    private void validateMovementOriginFrom(ChessPosition sourcePosition) {
        validatePiecePresenceOn(sourcePosition);
        validatePieceMobility(sourcePosition);
    }

    private void validatePieceMobility(ChessPosition sourcePosition) {
        ChessPiece piece = (ChessPiece) board.getPiecePlacedOn(sourcePosition);

        if (piece.hasNoAvailableMovements())
            throw new ChessException("There is no possible movements for the chosen " + piece + " piece");
    }

    private void validatePiecePresenceOn(ChessPosition sourcePosition) {
        if (board.isBoardPositionEmpty(sourcePosition))
            throw new ChessException("There is no piece present on source position " + sourcePosition);
    }

    private void validateTargetPositionAvailability(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessPiece piece = (ChessPiece) board.getPiecePlacedOn(sourcePosition);

        if (piece.canNotTargetThis(targetPosition))
            throw new ChessException("The chosen piece cannot move to target position " + targetPosition);

    }

}
