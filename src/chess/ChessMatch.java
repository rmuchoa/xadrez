package chess;

import boardgame.Board;
import boardgame.BoardPiece;
import boardgame.BoardPosition;
import chess.pieces.King;
import chess.pieces.Rook;

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
                chessPieces[row][column] = (ChessPiece) board.getBoardPiecePlacedOn(row, column);
            }
        }

        return chessPieces;
    }

    public ChessPiece performChessMove(ChessPosition source, ChessPosition target) {
        validateSourcePositionExistence(source);

        BoardPiece capturedPiece = makeMove(source.toBoardPosition(), target.toBoardPosition());
        return (ChessPiece) capturedPiece;
    }

    private BoardPiece makeMove(BoardPosition source, BoardPosition target) {
        BoardPiece movingPiece = board.removePieceFrom(source);
        BoardPiece capturedPiece = board.removePieceFrom(target);

        board.placePieceOn(target, movingPiece);

        return capturedPiece;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        ChessPosition chessPosition = new ChessPosition(column, row);
        board.placePieceOn(chessPosition.toBoardPosition(), piece);
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

    private void validateSourcePositionExistence(ChessPosition source) {
        if (board.isBoardPositionEmpty(source.toBoardPosition())) {
            throw new ChessException("There is no piece on source position "+source.getColumn()+source.getRow());
        }
    }

}
