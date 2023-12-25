package chess.pieces;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    private King(ChessBoard board, Color color) {
        super(board, color);
    }

    @Override
    public List<ChessPosition> getAllAvailableTargetPositions() {
        List<ChessPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableStraightPositions());
        possibleMovements.addAll(getAllAvailableDiagonalPositions());

        return possibleMovements;
    }

    private List<ChessPosition> getAllAvailableStraightPositions() {
        List<ChessPosition> straightMovements = new ArrayList<>();

        ifAllowedAddNextAbovePositionOn(straightMovements);
        ifAllowedAddNextBelowPositionOn(straightMovements);
        ifAllowedAddNextRightPositionOn(straightMovements);
        ifAllowedAddNextLeftPositionOn(straightMovements);

        return straightMovements;
    }

    private List<ChessPosition> getAllAvailableDiagonalPositions() {
        List<ChessPosition> diagonalMovements = new ArrayList<>();

        ifAllowedAddNextSuperiorRightPositionOn(diagonalMovements);
        ifAllowedAddNextSuperiorLeftPositionOn(diagonalMovements);
        ifAllowedAddNextInferiorRightPositionOn(diagonalMovements);
        ifAllowedAddNextInferiorLeftPositionOn(diagonalMovements);

        return diagonalMovements;
    }

    private void ifAllowedAddNextAbovePositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition abovePosition = getPosition().getNextAbovePosition();

            if (isAllowedToTarget(abovePosition))
                possibleMovements.add(abovePosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextBelowPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition belowPosition = getPosition().getNextBelowPosition();

            if (isAllowedToTarget(belowPosition))
                possibleMovements.add(belowPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextRightPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition rightPosition = getPosition().getNextRightPosition();

            if (isAllowedToTarget(rightPosition))
                possibleMovements.add(rightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextLeftPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition leftPosition = getPosition().getNextLeftPosition();

            if (isAllowedToTarget(leftPosition))
                possibleMovements.add(leftPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextSuperiorRightPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition superiorRightPosition = getPosition().getNextDiagonalSuperiorRightPosition();

            if (isAllowedToTarget(superiorRightPosition))
                possibleMovements.add(superiorRightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextSuperiorLeftPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition superiorLeftPosition = getPosition().getNextDiagonalSuperiorLeftPosition();

            if (isAllowedToTarget(superiorLeftPosition))
                possibleMovements.add(superiorLeftPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextInferiorRightPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition inferiorRightPosition = getPosition().getNextDiagonalInferiorRightPosition();

            if (isAllowedToTarget(inferiorRightPosition))
                possibleMovements.add(inferiorRightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextInferiorLeftPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition inferiorLeftPosition = getPosition().getNextDiagonalInferiorLeftPosition();

            if (isAllowedToTarget(inferiorLeftPosition))
                possibleMovements.add(inferiorLeftPosition);

        } catch (ChessException ignored) {}
    }

    @Override
    public String toString() {
        return "K";
    }

    public static KingBuilder builder() {
        return KingBuilder.builder();
    }

    public static class KingBuilder {

        private ChessBoard board;
        private Color color;

        private KingBuilder() {}

        public static KingBuilder builder() {
            return new KingBuilder();
        }

        public KingBuilder board(ChessBoard board) {
            this.board = board;
            return this;
        }

        public KingBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public King build() {
            return new King(board, color);
        }

    }

}
