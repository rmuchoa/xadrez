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
public class Rook extends ChessPiece {

    protected Rook(ChessBoard board, ChessMatch match, Color color) {
        super(board, match, color);
    }

    @Override
    public List<ChessPosition> getAllAvailableTargetPositions() {
        List<ChessPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableNorthPositions());
        possibleMovements.addAll(getAllAvailableSouthPositions());
        possibleMovements.addAll(getAllAvailableEastPositions());
        possibleMovements.addAll(getAllAvailableWestPositions());

        return possibleMovements;
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

    public boolean isAllowedToCastling(boolean kingside) {
        if (hasNotMovedYet()) {
            if (kingside) {
                ChessPosition step1 = getPosition().getNextBesideWestCastlingMovementPosition();
                ChessPosition step2 = step1.getNextBesideWestCastlingMovementPosition();

                return doesExistsOnBoard(step1) && thereIsNoPiecePlacedOn(step1) &&
                    doesExistsOnBoard(step2) && thereIsNoPiecePlacedOn(step2);

            } else {
                ChessPosition step1 = getPosition().getNextBesideEastCastlingMovementPosition();
                ChessPosition step2 = step1.getNextBesideEastCastlingMovementPosition();
                ChessPosition step3 = step2.getNextBesideEastCastlingMovementPosition();

                return doesExistsOnBoard(step1) && thereIsNoPiecePlacedOn(step1) &&
                    doesExistsOnBoard(step2) && thereIsNoPiecePlacedOn(step2) &&
                    doesExistsOnBoard(step3) && thereIsNoPiecePlacedOn(step3);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "R";
    }

}
