package chess.pieces;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessPosition.MovementType;
import chess.Color;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Pawn extends ChessPiece {

    protected Pawn(ChessBoard board, ChessMatch match, Color color) {
        super(board, match, color);
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
            ifAllowedAddNorthEastPositionOn(takingPositions);
            ifAllowedAddNorthWestPositionOn(takingPositions);
        } else {
            ifAllowedAddSouthEastPositionOn(takingPositions);
            ifAllowedAddSouthWestPositionOn(takingPositions);
        }

        return takingPositions;
    }

    private void ifAllowedAddSingleWalkingPositionOn(List<ChessPosition> walkingPositions) {
        if (isWhitePiece())
            ifAllowedAddSingleNorthPositionOn(walkingPositions);
        else
            ifAllowedAddSingleSouthPositionOn(walkingPositions);
    }

    private void ifAllowedAddTwoWalkingPositionOn(List<ChessPosition> walkingPositions) {
        if (isWhitePiece())
            ifAllowedAddTwoNorthPositionsOn(walkingPositions);
        else
            ifAllowedAddTwoSouthPositionsOn(walkingPositions);
    }

    private void ifAllowedAddSingleNorthPositionOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition northPosition = getPosition().getNextNorthPosition();

            if (isAllowedToTarget(northPosition))
                walkingPositions.add(northPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSingleSouthPositionOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition southPosition = getPosition().getNextSouthPosition();

            if (isAllowedToTarget(southPosition))
                walkingPositions.add(southPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddTwoNorthPositionsOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition northPosition = getPosition().getNextNorthPosition();

            if (isAllowedToTarget(northPosition))
                walkingPositions.add(northPosition);

            ChessPosition secondNorthPosition = northPosition.getNextNorthPosition();
            if (isAllowedToTarget(secondNorthPosition))
                walkingPositions.add(secondNorthPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddTwoSouthPositionsOn(List<ChessPosition> walkingPositions) {
        try {
            ChessPosition southPosition = getPosition().getNextSouthPosition();

            if (isAllowedToTarget(southPosition))
                walkingPositions.add(southPosition);

            ChessPosition secondSouthPosition = southPosition.getNextSouthPosition();
            if (isAllowedToTarget(secondSouthPosition))
                walkingPositions.add(secondSouthPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNorthEastPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition northEastPosition = getPosition().getNextNorthEastPosition();

            if (isAllowedToTarget(northEastPosition))
                takingPositions.add(northEastPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddNorthWestPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition northWestPosition = getPosition().getNextNorthWestPosition();

            if (isAllowedToTarget(northWestPosition))
                takingPositions.add(northWestPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSouthEastPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition southEastPosition = getPosition().getNextSouthEastPosition();

            if (isAllowedToTarget(southEastPosition))
                takingPositions.add(southEastPosition);

        } catch (ChessException ignored) {}
    }

    private void ifAllowedAddSouthWestPositionOn(List<ChessPosition> takingPositions) {
        try {
            ChessPosition southWestPosition = getPosition().getNextSouthWestPosition();

            if (isAllowedToTarget(southWestPosition))
                takingPositions.add(southWestPosition);

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

}
