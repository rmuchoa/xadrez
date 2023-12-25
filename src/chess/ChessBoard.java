package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Empty;
import java.util.List;

public class ChessBoard extends Board<ChessPosition, ChessPiece, ChessBoard> {

    public ChessBoard(int totalRows, int totalColumns) {
        super(totalRows, totalColumns);
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

        private int totalRows;
        private int totalColumns;

        private ChessBoardBuilder() {}

        public static ChessBoardBuilder builder() {
            return new ChessBoardBuilder();
        }

        public ChessBoardBuilder totalRows(int totalRows) {
            this.totalRows = totalRows;
            return this;
        }

        public ChessBoardBuilder totalColumns(int totalColumns) {
            this.totalColumns = totalColumns;
            return this;
        }

        public ChessBoard build() {
            return new ChessBoard(totalRows, totalColumns);
        }

    }

}
