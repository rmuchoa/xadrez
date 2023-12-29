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
public class Queen extends ChessPiece {

    private Queen(ChessBoard board, ChessMatch match, Color color) {
        super(board, match, color);
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

        straightMovements.addAll(getAllAvailableNorthPositions());
        straightMovements.addAll(getAllAvailableSouthPositions());
        straightMovements.addAll(getAllAvailableEastPositions());
        straightMovements.addAll(getAllAvailableWestPositions());

        return straightMovements;
    }

    private List<ChessPosition> getAllAvailableDiagonalPositions() {
        List<ChessPosition> diagonalMovements = new ArrayList<>();

        diagonalMovements.addAll(getAllAvailableNorthEastPositions());
        diagonalMovements.addAll(getAllAvailableNorthWestPositions());
        diagonalMovements.addAll(getAllAvailableSouthEastPositions());
        diagonalMovements.addAll(getAllAvailableSouthWestPositions());

        return diagonalMovements;
    }

    private List<ChessPosition> getAllAvailableNorthPositions() {
        List<ChessPosition> northMovements = new ArrayList<>();

        try {
            appendNextAvailableNorthPosition(northMovements, getPosition());

        } catch (ChessException ignored) {}

        return northMovements;
    }

    private void appendNextAvailableNorthPosition(List<ChessPosition> northMovements, ChessPosition position) {
        ChessPosition northPosition = position.getNextNorthPosition();

        if (isAllowedToTarget(northPosition)) {
            northMovements.add(northPosition);

            if (thereIsAnOpponentPlacedOn(northPosition))
                return;

            appendNextAvailableNorthPosition(northMovements, northPosition);
        }
    }

    private List<ChessPosition> getAllAvailableSouthPositions() {
        List<ChessPosition> southMovements = new ArrayList<>();

        try {
            appendNextAvailableSouthPosition(southMovements, getPosition());

        } catch (ChessException ignored) {}

        return southMovements;
    }

    private void appendNextAvailableSouthPosition(List<ChessPosition> southMovements, ChessPosition position) {
        ChessPosition southPosition = position.getNextSouthPosition();

        if (isAllowedToTarget(southPosition)) {
            southMovements.add(southPosition);

            if (thereIsAnOpponentPlacedOn(southPosition))
                return;

            appendNextAvailableSouthPosition(southMovements, southPosition);
        }
    }

    private List<ChessPosition> getAllAvailableEastPositions() {
        List<ChessPosition> eastMovements = new ArrayList<>();

        try {
            appendNextAvailableEastPosition(eastMovements, getPosition());

        } catch (ChessException ignored) {}

        return eastMovements;
    }

    private void appendNextAvailableEastPosition(List<ChessPosition> eastMovements, ChessPosition position) {
        ChessPosition eastPosition = position.getNextEastPosition();

        if (isAllowedToTarget(eastPosition)) {
            eastMovements.add(eastPosition);

            if (thereIsAnOpponentPlacedOn(eastPosition))
                return;

            appendNextAvailableEastPosition(eastMovements, eastPosition);
        }
    }

    private List<ChessPosition> getAllAvailableWestPositions() {
        List<ChessPosition> westMovements = new ArrayList<>();

        try {
            appendNextAvailableWestPosition(westMovements, getPosition());

        } catch (ChessException ignored) {}

        return westMovements;
    }

    private void appendNextAvailableWestPosition(List<ChessPosition> westMovements, ChessPosition position) {
        ChessPosition westPosition = position.getNextWestPosition();

        if (isAllowedToTarget(westPosition)) {
            westMovements.add(westPosition);

            if (thereIsAnOpponentPlacedOn(westPosition))
                return;

            appendNextAvailableWestPosition(westMovements, westPosition);
        }
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
        List<ChessPosition> northWestMovements = new ArrayList<>();

        try {
            appendNextAvailableNorthWestPosition(northWestMovements, getPosition());

        } catch (ChessException ignored) {}

        return northWestMovements;
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
        return "Q";
    }

}
