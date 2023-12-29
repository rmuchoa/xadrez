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
public class Bishop extends ChessPiece {

    private Bishop(ChessBoard board, ChessMatch match, Color color) {
        super(board, match, color);
    }

    @Override
    public List<ChessPosition> getAllAvailableTargetPositions() {
        List<ChessPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableNorthEastPositions());
        possibleMovements.addAll(getAllAvailableNorthWestPositions());
        possibleMovements.addAll(getAllAvailableSouthEastPositions());
        possibleMovements.addAll(getAllAvailableSouthWestPositions());

        return possibleMovements;
    }

    private List<ChessPosition> getAllAvailableNorthEastPositions() {
        List<ChessPosition> northEastMovements = new ArrayList<>();

        try {
            appendNextAvailableNorthEastPosition(northEastMovements, getPosition());

        } catch (ChessException ignored) {}

        return northEastMovements;
    }

    private void appendNextAvailableNorthEastPosition(List<ChessPosition> northEastMovements, ChessPosition position) {
        ChessPosition northEastPosition = position.getNextNorthEastPosition();

        if (isAllowedToTarget(northEastPosition)) {
            northEastMovements.add(northEastPosition);

            if (thereIsAnOpponentPlacedOn(northEastPosition))
                return;

            appendNextAvailableNorthEastPosition(northEastMovements, northEastPosition);
        }
    }

    private List<ChessPosition> getAllAvailableNorthWestPositions() {
        List<ChessPosition> northWeastMovements = new ArrayList<>();

        try {
            appendNextAvailableNorthWestPosition(northWeastMovements, getPosition());

        } catch (ChessException ignored) {}

        return northWeastMovements;
    }

    private void appendNextAvailableNorthWestPosition(List<ChessPosition> northWestMovements, ChessPosition position) {
        ChessPosition northWestPosition = position.getNextNorthWestPosition();

        if (isAllowedToTarget(northWestPosition)) {
            northWestMovements.add(northWestPosition);

            if (thereIsAnOpponentPlacedOn(northWestPosition))
                return;

            appendNextAvailableNorthWestPosition(northWestMovements, northWestPosition);
        }
    }

    private List<ChessPosition> getAllAvailableSouthEastPositions() {
        List<ChessPosition> southEastMovements = new ArrayList<>();

        try {
            appendNextAvailableSouthEastPosition(southEastMovements, getPosition());

        } catch (ChessException ignored) {}

        return southEastMovements;
    }

    private void appendNextAvailableSouthEastPosition(List<ChessPosition> southEastMovements, ChessPosition position) {
        ChessPosition southEastPosition = position.getNextSouthEastPosition();

        if (isAllowedToTarget(southEastPosition)) {
            southEastMovements.add(southEastPosition);

            if (thereIsAnOpponentPlacedOn(southEastPosition))
                return;

            appendNextAvailableSouthEastPosition(southEastMovements, southEastPosition);
        }
    }

    private List<ChessPosition> getAllAvailableSouthWestPositions() {
        List<ChessPosition> southWestMovements = new ArrayList<>();

        try {
            appendNextAvailableSouthWestPosition(southWestMovements, getPosition());

        } catch (ChessException ignored) {}

        return southWestMovements;
    }

    private void appendNextAvailableSouthWestPosition(List<ChessPosition> southWestMovements, ChessPosition position) {
        ChessPosition southWestPosition = position.getNextSouthWestPosition();

        if (isAllowedToTarget(southWestPosition)) {
            southWestMovements.add(southWestPosition);

            if (thereIsAnOpponentPlacedOn(southWestPosition))
                return;

            appendNextAvailableSouthWestPosition(southWestMovements, southWestPosition);
        }
    }

    @Override
    public String toString() {
        return "B";
    }

}
