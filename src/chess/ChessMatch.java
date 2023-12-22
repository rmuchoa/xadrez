package chess;

import boardgame.Board;
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

        for (int i=0; i<board.getTotalRows(); i++) {
            for (int j=0; j<board.getTotalColumns(); j++) {
                chessPieces[i][j] = (ChessPiece) board.getBoardPiecePlacedOn(i, j);
            }
        }

        return chessPieces;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        ChessPosition chessPosition = new ChessPosition(column, row);
        board.placePieceOn(chessPosition.toBoardPosition(), piece);
    }

    private void initialSetup() {
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE));
        placeNewPiece('e', 8, new King(board, Color.BLACK));
    }

}
