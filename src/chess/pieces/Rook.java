package chess.pieces;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    private Rook(ChessBoard board, ChessMatch match, Color color) {
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

    @Override
    public String toString() {
        return "R";
    }
    
    public static RookBuilder builder() {
        return RookBuilder.builder();
    }
    
    public static class RookBuilder {

        private ChessBoard board;
        private ChessMatch match;
        private Color color;

        private RookBuilder() {}

        public RookBuilder board(ChessBoard board) {
            this.board = board;
            return this;
        }

        public RookBuilder match(ChessMatch match) {
            this.match = match;
            return this;
        }

        public RookBuilder color(Color color) {
            this.color = color;
            return this;
        }
        
        public static RookBuilder builder() {
            return new RookBuilder();
        }
        
        public Rook build() {
            return new Rook(board, match, color);
        }
        
    }

}
