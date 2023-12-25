package chess.pieces;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessPosition.MovementType;
import chess.Color;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends ChessPiece {

    protected Pawn(ChessBoard board, Color color) {
        super(board, color);
    }

    @Override
    public List<ChessPosition> getAllAvailableTargetPositions() {
        List<ChessPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableWalkingPositions());
        possibleMovements.addAll(getAllAvailableTakingPositions());

        return possibleMovements;
    }

    private List<ChessPosition> getAllAvailableWalkingPositions() {
        List<ChessPosition> walkingPositions = new ArrayList<>();

        if (getMoveCount() > 0)
            ifAllowedAddSingleWalkingPositionOn(walkingPositions);
        else
            ifAllowedAddTwoWalkingPositionOn(walkingPositions);

        return walkingPositions;
    }

    private List<ChessPosition> getAllAvailableTakingPositions() {
        List<ChessPosition> takingPositions = new ArrayList<>();

        if (isWhitePiece()) {
            ifAllowedAddSuperiorRightDiagonalPositionOn(takingPositions);
            ifAllowedAddSuperiorLeftDiagonalPositionOn(takingPositions);
        } else {
            ifAllowedAddInferiorRightDiagonalPositionOn(takingPositions);
            ifAllowedAddInferiorLeftDiagonalPositionOn(takingPositions);
        }

        return takingPositions;
    }

    private void ifAllowedAddSingleWalkingPositionOn(List<ChessPosition> walkingPositions) {
        if (isWhitePiece())
            ifAllowedAddSingleAbovePositionOn(walkingPositions);
        else
            ifAllowedAddSingleBelowPositionOn(walkingPositions);
    }

    private void ifAllowedAddTwoWalkingPositionOn(List<ChessPosition> walkingPositions) {
        if (isWhitePiece())
            ifAllowedAddTwoAbovePositionOn(walkingPositions);
        else
            ifAllowedAddTwoBelowPositionOn(walkingPositions);
    }

    private void ifAllowedAddSingleAbovePositionOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition abovePosition = getPosition().getNextAbovePosition();

            if (isAllowedToTarget(abovePosition))
                walkingPositions.add(abovePosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSingleBelowPositionOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition belowPosition = getPosition().getNextBelowPosition();

            if (isAllowedToTarget(belowPosition))
                walkingPositions.add(belowPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddTwoAbovePositionOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition abovePosition = getPosition().getNextAbovePosition();

            if (isAllowedToTarget(abovePosition))
                walkingPositions.add(abovePosition);

            ChessPosition secondAbovePosition = abovePosition.getNextAbovePosition();
            if (isAllowedToTarget(secondAbovePosition))
                walkingPositions.add(secondAbovePosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddTwoBelowPositionOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition belowPosition = getPosition().getNextBelowPosition();

            if (isAllowedToTarget(belowPosition))
                walkingPositions.add(belowPosition);

            ChessPosition secondBelowPosition = belowPosition.getNextBelowPosition();
            if (isAllowedToTarget(secondBelowPosition))
                walkingPositions.add(secondBelowPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSuperiorRightDiagonalPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition superiorRightPosition = getPosition().getNextDiagonalSuperiorRightPosition();

            if (isAllowedToTarget(superiorRightPosition))
                takingPositions.add(superiorRightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSuperiorLeftDiagonalPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition superiorLeftPosition = getPosition().getNextDiagonalSuperiorLeftPosition();

            if (isAllowedToTarget(superiorLeftPosition))
                takingPositions.add(superiorLeftPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddInferiorRightDiagonalPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition inferiorRightPosition = getPosition().getNextDiagonalInferiorRightPosition();

            if (isAllowedToTarget(inferiorRightPosition))
                takingPositions.add(inferiorRightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddInferiorLeftDiagonalPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition inferiorLeftPosition = getPosition().getNextDiagonalInferiorLeftPosition();

            if (isAllowedToTarget(inferiorLeftPosition))
                takingPositions.add(inferiorLeftPosition);

        } catch (ChessException ignored) {}
    }

    @Override
    protected boolean isAllowedToTarget(ChessPosition position) {
        if (doesNotExistsOnBoard(position))
            return false;

        return isNonBlockedFrontalWalkingMovement(position) ||
            isDiagonalTakingMovement(position);

    }

    private boolean isNonBlockedFrontalWalkingMovement(ChessPosition position) {
        return MovementType.LINE.equals(position.getMovement()) &&
            thereIsNoPiecePlacedOn(position);
    }

    private boolean isDiagonalTakingMovement(ChessPosition position) {
        return MovementType.DIAGONAL.equals(position.getMovement()) &&
            thereIsAnOpponentPlacedOn(position);
    }

    @Override
    public String toString() {
        return "P";
    }

    public static PawnBuilder builder() {
        return PawnBuilder.builder();
    }

    public static class PawnBuilder {

        private ChessBoard board;
        private Color color;

        private PawnBuilder() {}

        public static PawnBuilder builder() {
            return new PawnBuilder();
        }

        public PawnBuilder board(ChessBoard board) {
            this.board = board;
            return this;
        }

        public PawnBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public Pawn build() {
            return new Pawn(board, color);
        }

    }

}
