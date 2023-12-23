package chess.pieces;

import boardgame.Board;
import chess.ChessException;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public King(Board board, Color color) {
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
        ChessPosition current = getPosition();

        List<ChessPosition> straightMovements = new ArrayList<>();
        ifAllowedAddNextAbovePositionOn(straightMovements, current);
        ifAllowedAddNextBelowPositionOn(straightMovements, current);
        ifAllowedAddNextRightPositionOn(straightMovements, current);
        ifAllowedAddNextLeftPositionOn(straightMovements, current);

        return straightMovements;
    }

    private List<ChessPosition> getAllAvailableDiagonalPositions() {
        ChessPosition current = getPosition();

        List<ChessPosition> diagonalMovements = new ArrayList<>();
        ifAllowedAddNextSuperiorRightPositionOn(diagonalMovements, current);
        ifAllowedAddNextSuperiorLeftPositionOn(diagonalMovements, current);
        ifAllowedAddNextInferiorRightPositionOn(diagonalMovements, current);
        ifAllowedAddNextInferiorLeftPositionOn(diagonalMovements, current);

        return diagonalMovements;
    }

    private void ifAllowedAddNextAbovePositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition abovePosition = current.getNextAbovePosition();

            if (isAllowedToTarget(abovePosition))
                possibleMovements.add(abovePosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextBelowPositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition belowPosition = current.getNextBelowPosition();

            if (isAllowedToTarget(belowPosition))
                possibleMovements.add(belowPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextRightPositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition rightPosition = current.getNextRightPosition();

            if (isAllowedToTarget(rightPosition))
                possibleMovements.add(rightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextLeftPositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition leftPosition = current.getNextLeftPosition();

            if (isAllowedToTarget(leftPosition))
                possibleMovements.add(leftPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextSuperiorRightPositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition superiorRightPosition = current.getNextDiagonalSuperiorRightPosition();

            if (isAllowedToTarget(superiorRightPosition))
                possibleMovements.add(superiorRightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextSuperiorLeftPositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition superiorLeftPosition = current.getNextDiagonalSuperiorLeftPosition();

            if (isAllowedToTarget(superiorLeftPosition))
                possibleMovements.add(superiorLeftPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextInferiorRightPositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition inferiorRightPosition = current.getNextDiagonalInferiorRightPosition();

            if (isAllowedToTarget(inferiorRightPosition))
                possibleMovements.add(inferiorRightPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextInferiorLeftPositionOn(List<ChessPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition inferiorLeftPosition = current.getNextDiagonalInferiorLeftPosition();

            if (isAllowedToTarget(inferiorLeftPosition))
                possibleMovements.add(inferiorLeftPosition);

        } catch (ChessException ignored) {}
    }

    @Override
    public String toString() {
        return "K";
    }
}
