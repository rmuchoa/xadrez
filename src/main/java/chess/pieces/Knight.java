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

}
