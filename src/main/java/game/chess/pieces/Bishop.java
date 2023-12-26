package game.chess.pieces;

import game.chess.ChessBoard;
import game.chess.ChessException;
import game.chess.ChessMatch;
import game.chess.ChessPiece;
import game.chess.ChessPosition;
import game.chess.Color;
import java.util.ArrayList;
import java.util.List;

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
    
    public static BishopBuilder builder() {
        return BishopBuilder.builder();
    }
    
    public static class BishopBuilder {

        private ChessBoard board;
        private ChessMatch match;
        private Color color;

        private BishopBuilder() {}

        public BishopBuilder board(ChessBoard board) {
            this.board = board;
            return this;
        }

        public BishopBuilder match(ChessMatch match) {
            this.match = match;
            return this;
        }

        public BishopBuilder color(Color color) {
            this.color = color;
            return this;
        }
        
        public static BishopBuilder builder() {
            return new BishopBuilder();
        }
        
        public Bishop build() {
            return new Bishop(board, match, color);
        }
        
    }

}
