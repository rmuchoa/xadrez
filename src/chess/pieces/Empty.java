package chess.pieces;

import chess.ChessBoard;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.Collections;
import java.util.List;

public class Empty extends ChessPiece {

    private static final Color NO_COLOR = null;

    public Empty(ChessBoard board, ChessMatch match) {
        super(board, match, NO_COLOR);
    }

    public List<ChessPosition> getAllAvailableTargetPositions() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "-";
    }

    public static EmptyBuilder builder() {
        return EmptyBuilder.builder();
    }

    public static class EmptyBuilder {

        private ChessBoard board;
        private ChessMatch match;

        private EmptyBuilder() {}

        public static EmptyBuilder builder() {
            return new EmptyBuilder();
        }

        public EmptyBuilder board(ChessBoard board) {
            this.board = board;
            return this;
        }

        public EmptyBuilder match(ChessMatch match) {
            this.match = match;
            return this;
        }

        public Empty build() {
            return new Empty(board, match);
        }

    }

}
