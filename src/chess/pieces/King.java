package chess.pieces;

import boardgame.Board;
import boardgame.BoardPosition;
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
    public List<BoardPosition> getAllAvailableTargetPositions() {
        List<BoardPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableStraightPositions());
        possibleMovements.addAll(getAllAvailableDiagonalPositions());

        return possibleMovements;
    }

    private List<BoardPosition> getAllAvailableStraightPositions() {
        ChessPosition current = ChessPosition.fromPosition(getPosition());

        List<BoardPosition> straightMovements = new ArrayList<>();
        ifAllowedAddNextAbovePositionOn(straightMovements, current);
        ifAllowedAddNextBelowPositionOn(straightMovements, current);
        ifAllowedAddNextRightPositionOn(straightMovements, current);
        ifAllowedAddNextLeftPositionOn(straightMovements, current);

        return straightMovements;
    }

    private List<BoardPosition> getAllAvailableDiagonalPositions() {
        ChessPosition current = ChessPosition.fromPosition(getPosition());

        List<BoardPosition> diagonalMovements = new ArrayList<>();
        ifAllowedAddNextSuperiorRightPositionOn(diagonalMovements, current);
        ifAllowedAddNextSuperiorLeftPositionOn(diagonalMovements, current);
        ifAllowedAddNextInferiorRightPositionOn(diagonalMovements, current);
        ifAllowedAddNextInferiorLeftPositionOn(diagonalMovements, current);

        return diagonalMovements;
    }

    private void ifAllowedAddNextAbovePositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition abovePosition = current.getNextAbovePosition();

            if (isAllowedToTarget(abovePosition))
                possibleMovements.add(abovePosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextBelowPositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition belowPosition = current.getNextBelowPosition();

            if (isAllowedToTarget(belowPosition))
                possibleMovements.add(belowPosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextRightPositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition rightPosition = current.getNextRightPosition();

            if (isAllowedToTarget(rightPosition))
                possibleMovements.add(rightPosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextLeftPositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition leftPosition = current.getNextLeftPosition();

            if (isAllowedToTarget(leftPosition))
                possibleMovements.add(leftPosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextSuperiorRightPositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition superiorRightPosition = current.getNextDiagonalSuperiorRightPosition();

            if (isAllowedToTarget(superiorRightPosition))
                possibleMovements.add(superiorRightPosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextSuperiorLeftPositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition superiorLeftPosition = current.getNextDiagonalSuperiorLeftPosition();

            if (isAllowedToTarget(superiorLeftPosition))
                possibleMovements.add(superiorLeftPosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextInferiorRightPositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition inferiorRightPosition = current.getNextDiagonalInferiorRightPosition();

            if (isAllowedToTarget(inferiorRightPosition))
                possibleMovements.add(inferiorRightPosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNextInferiorLeftPositionOn(List<BoardPosition> possibleMovements, ChessPosition current) {
        try {
            ChessPosition inferiorLeftPosition = current.getNextDiagonalInferiorLeftPosition();

            if (isAllowedToTarget(inferiorLeftPosition))
                possibleMovements.add(inferiorLeftPosition.toBoardPosition());

        } catch (ChessException ignored) {}
    }

    @Override
    public String toString() {
        return "K";
    }
}
