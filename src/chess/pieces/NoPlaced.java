package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.Collections;
import java.util.List;

public class NoPlaced extends ChessPiece {

    private static final Color NO_COLOR = null;

    public NoPlaced(Board board) {
        super(board, NO_COLOR);
    }

    public List<ChessPosition> getAllAvailableTargetPositions() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "-";
    }

    public static NoPlacedBuilder builder() {
        return NoPlacedBuilder.builder();
    }

    public static class NoPlacedBuilder {

        private Board board;

        private NoPlacedBuilder() {}

        public static NoPlacedBuilder builder() {
            return new NoPlacedBuilder();
        }

        public NoPlacedBuilder board(Board board) {
            this.board = board;
            return this;
        }

        public NoPlaced build() {
            return new NoPlaced(board);
        }

    }

}
