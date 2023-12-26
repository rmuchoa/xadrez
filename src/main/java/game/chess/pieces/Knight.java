package game.chess.pieces;

import game.chess.ChessBoard;
import game.chess.ChessException;
import game.chess.ChessMatch;
import game.chess.ChessPiece;
import game.chess.ChessPosition;
import game.chess.Color;
import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    private Knight(ChessBoard board, ChessMatch match, Color color) {
        super(board, match, color);
    }

    @Override
    public List<ChessPosition> getAllAvailableTargetPositions() {
        List<ChessPosition> possibleMovements = new ArrayList<>();

        ifAllowedAddSuperiorNorthEastElMovementPositionOn(possibleMovements);
        ifAllowedAddSuperiorNorthWestElMovementPositionOn(possibleMovements);
        ifAllowedAddInferiorNorthEastElMovementPositionOn(possibleMovements);
        ifAllowedAddInferiorNorthWestElMovementPositionOn(possibleMovements);

        ifAllowedAddSuperiorSouthEastElMovementPositionOn(possibleMovements);
        ifAllowedAddSuperiorSouthWestElMovementPositionOn(possibleMovements);
        ifAllowedAddInferiorSouthEastElMovementPositionOn(possibleMovements);
        ifAllowedAddInferiorSouthWestElMovementPositionOn(possibleMovements);

        return possibleMovements;
    }

    private void ifAllowedAddSuperiorNorthEastElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition superiorNorthEastElMovementPosition = getPosition().getSuperiorNorthEastElMovementPosition();

            if (isAllowedToTarget(superiorNorthEastElMovementPosition))
                possibleMovements.add(superiorNorthEastElMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSuperiorNorthWestElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition superiorNorthWestElMovementPosition = getPosition().getSuperiorNorthWestElMovementPosition();

            if (isAllowedToTarget(superiorNorthWestElMovementPosition))
                possibleMovements.add(superiorNorthWestElMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddInferiorNorthEastElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition inferiorNorthEastElMovementPosition = getPosition().getInferiorNorthEastElMovementPosition();

            if (isAllowedToTarget(inferiorNorthEastElMovementPosition))
                possibleMovements.add(inferiorNorthEastElMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddInferiorNorthWestElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition inferiorNorthWestElMovementPosition = getPosition().getInferiorNorthWestElMovementPosition();

            if (isAllowedToTarget(inferiorNorthWestElMovementPosition))
                possibleMovements.add(inferiorNorthWestElMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSuperiorSouthEastElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition superiorSouthEastElMovementPosition = getPosition().getSuperiorSouthEastElMovementPosition();

            if (isAllowedToTarget(superiorSouthEastElMovementPosition))
                possibleMovements.add(superiorSouthEastElMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSuperiorSouthWestElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition superiorSouthWestElMovementPosition = getPosition().getSuperiorSouthWestElMovementPosition();

            if (isAllowedToTarget(superiorSouthWestElMovementPosition))
                possibleMovements.add(superiorSouthWestElMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddInferiorSouthEastElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition inferiorSouthEastElMovementPosition = getPosition().getInferiorSouthEastElMovementPosition();

            if (isAllowedToTarget(inferiorSouthEastElMovementPosition))
                possibleMovements.add(inferiorSouthEastElMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddInferiorSouthWestElMovementPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition inferiorSouthWestElMovementPosition = getPosition().getInferiorSouthWestElMovementPosition();

            if (isAllowedToTarget(inferiorSouthWestElMovementPosition))
                possibleMovements.add(inferiorSouthWestElMovementPosition);

        } catch (ChessException ignored) {}
    }

    @Override
    public String toString() {
        return "N";
    }

    public static KnightBuilder builder() {
        return KnightBuilder.builder();
    }

    public static class KnightBuilder {

        private ChessBoard board;
        private ChessMatch match;
        private Color color;

        private KnightBuilder() {}

        public static KnightBuilder builder() {
            return new KnightBuilder();
        }

        public KnightBuilder board(ChessBoard board) {
            this.board = board;
            return this;
        }

        public KnightBuilder match(ChessMatch match) {
            this.match = match;
            return this;
        }

        public KnightBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public Knight build() {
            return new Knight(board, match, color);
        }

    }

}
