package chess;

import boardgame.Board;
import chess.pieces.King;
import java.util.List;

public class ChessBoard extends Board<ChessPosition, ChessPiece, ChessBoard> {

    public static final int CHESS_BOARD_SIZE = 8;

    public ChessBoard() {
        super(CHESS_BOARD_SIZE, CHESS_BOARD_SIZE);
    }

    public List<ChessPiece> getAllPlacedPiecesOf(Color color) {
        return getAllPlacedPieces().stream()
            .filter(piece -> piece.hasSameColorOf(color))
            .toList();
    }

    public static ChessBoardBuilder builder() {
        return ChessBoardBuilder.builder();
    }

    public King getOpponentKingFrom(Color currentPlayer) {
        Color oponentColor = Color.WHITE.equals(currentPlayer) ? Color.BLACK : Color.WHITE;
        return getKingOf(oponentColor);
    }

    public King getKingOf(Color color) {
        return getAllPlacedPiecesOf(color)
            .stream()
            .filter(ChessPiece::isKing)
            .map(piece -> (King) piece)
            .findAny()
            .orElse(null);
    }

    public static class ChessBoardBuilder {

        private ChessBoardBuilder() {}

        public static ChessBoardBuilder builder() {
            return new ChessBoardBuilder();
        }

        public ChessBoard build() {
            return new ChessBoard();
        }

    }

}
