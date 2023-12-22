package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

    public static final int CHESS_BOARD_SIZE = 8;

    private Board board;

    public ChessMatch() {
        board = new Board(CHESS_BOARD_SIZE,CHESS_BOARD_SIZE);
        initialSetup();
    }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] chessPieces = new ChessPiece[CHESS_BOARD_SIZE][CHESS_BOARD_SIZE];

        for (int i=0; i<board.getRows(); i++) {
            for (int j=0; j<board.getColumns(); j++) {
                chessPieces[i][j] = (ChessPiece) board.getPieceFrom(i, j);
            }
        }

        return chessPieces;
    }

    private void initialSetup() {
        board.placePiece(new Rook(board, Color.WHITE), new Position(2, 1));
        board.placePiece(new King(board, Color.BLACK), new Position(0, 4));
        board.placePiece(new King(board, Color.WHITE), new Position(7, 4));

    }

}
