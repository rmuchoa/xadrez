package chess.pieces;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class King extends ChessPiece {

    private King(ChessBoard board, ChessMatch match, Color color) {
        super(board, match, color);
    }

    @Override
    public List<ChessPosition> getAllAvailableTargetPositions() {
        List<ChessPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableStraightPositions());
        possibleMovements.addAll(getAllAvailableDiagonalPositions());
        possibleMovements.addAll(getAllAvailableCastlingPositions());

        return possibleMovements;
    }

    private List<ChessPosition> getAllAvailableStraightPositions() {
        List<ChessPosition> straightMovements = new ArrayList<>();

        ifAllowedAddNorthPositionOn(straightMovements);
        ifAllowedAddSouthPositionOn(straightMovements);
        ifAllowedAddEastPositionOn(straightMovements);
        ifAllowedAddWestPositionOn(straightMovements);

        return straightMovements;
    }

    private List<ChessPosition> getAllAvailableDiagonalPositions() {
        List<ChessPosition> diagonalMovements = new ArrayList<>();

        ifAllowedAddNorthEastPositionOn(diagonalMovements);
        ifAllowedAddNorthWestPositionOn(diagonalMovements);
        ifAllowedAddSouthEastPositionOn(diagonalMovements);
        ifAllowedAddSouthWestPositionOn(diagonalMovements);

        return diagonalMovements;
    }

    private List<ChessPosition> getAllAvailableCastlingPositions() {
        List<ChessPosition> castlingMovements = new ArrayList<>();

        ifAllowedAddQueensideCastlingPositionOn(castlingMovements);
        ifAllowedAddKingsideCastlingPositionOn(castlingMovements);

        return castlingMovements;
    }

    private void ifAllowedAddNorthPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition northPosition = getPosition().getNextNorthPosition();

            if (isAllowedToTarget(northPosition))
                possibleMovements.add(northPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSouthPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition southPosition = getPosition().getNextSouthPosition();

            if (isAllowedToTarget(southPosition))
                possibleMovements.add(southPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddEastPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition eastPosition = getPosition().getNextEastPosition();

            if (isAllowedToTarget(eastPosition))
                possibleMovements.add(eastPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddWestPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition westPosition = getPosition().getNextWestPosition();

            if (isAllowedToTarget(westPosition))
                possibleMovements.add(westPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNorthEastPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition northEastPosition = getPosition().getNextNorthEastPosition();

            if (isAllowedToTarget(northEastPosition))
                possibleMovements.add(northEastPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNorthWestPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition northWestPosition = getPosition().getNextNorthWestPosition();

            if (isAllowedToTarget(northWestPosition))
                possibleMovements.add(northWestPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSouthEastPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition southEastPosition = getPosition().getNextSouthEastPosition();

            if (isAllowedToTarget(southEastPosition))
                possibleMovements.add(southEastPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSouthWestPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition southWestPosition = getPosition().getNextSouthWestPosition();

            if (isAllowedToTarget(southWestPosition))
                possibleMovements.add(southWestPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddQueensideCastlingPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition westCastlingMovementPosition = getPosition().getTwoBesideWestCastlingMovementPosition();

            if (isAllowedToCastling(westCastlingMovementPosition, false))
                possibleMovements.add(westCastlingMovementPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddKingsideCastlingPositionOn(List<ChessPosition> possibleMovements) {
        try {
            ChessPosition eastCastlingMovementPosition = getPosition().getTwoBesideEastCastlingMovementPosition();

            if (isAllowedToCastling(eastCastlingMovementPosition, true))
                possibleMovements.add(eastCastlingMovementPosition);

        } catch (ChessException ignored) {}
    }

    private boolean isAllowedToCastling(ChessPosition castlingTargetPosition, boolean kingside) {
        if (hasNotMovedYet() &&
            doesExistsOnBoard(castlingTargetPosition) &&
            thereIsNoPiecePlacedOn(castlingTargetPosition)) {

            if (kingside) {
                Rook rightRook = (Rook) getBoard().getPiecePlacedOn(getPosition().getThreeBesideEastCastlingMovementPosition());
                return rightRook != null && rightRook.isAllowedToCastling(kingside);
            } else {
                Rook leftRook = (Rook) getBoard().getPiecePlacedOn(getPosition().getFourBesideWestCastlingMovementPosition());
                return leftRook != null && leftRook.isAllowedToCastling(kingside);
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "K";
    }

}
