package chess.pieces;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    private Rook(ChessBoard board, Color color) {
        super(board, color);
    }

    @Override
    public List<ChessPosition> getAllAvailableTargetPositions() {
        List<ChessPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableAbovePositions());
        possibleMovements.addAll(getAllAvailableBelowPositions());
        possibleMovements.addAll(getAllAvailableRightPositions());
        possibleMovements.addAll(getAllAvailableLeftPositions());

        return possibleMovements;
    }

    private List<ChessPosition> getAllAvailableAbovePositions() {
        List<ChessPosition> aboveMovements = new ArrayList<>();

        try {
            appendNextAvailablePositionAbove(aboveMovements, getPosition());

        } catch (ChessException ignored) {}

        return aboveMovements;
    }

    private void appendNextAvailablePositionAbove(List<ChessPosition> aboveMovements, ChessPosition position) {
        ChessPosition abovePosition = position.getNextAbovePosition();

        if (isAllowedToTarget(abovePosition)) {
            aboveMovements.add(abovePosition);

            if (thereIsAnOpponentPlacedOn(abovePosition))
                return;

            appendNextAvailablePositionAbove(aboveMovements, abovePosition);
        }
    }

    private List<ChessPosition> getAllAvailableBelowPositions() {
        List<ChessPosition> belowMovements = new ArrayList<>();

        try {
            appendNextAvailablePositionBelow(belowMovements, getPosition());

        } catch (ChessException ignored) {}

        return belowMovements;
    }

    private void appendNextAvailablePositionBelow(List<ChessPosition> belowMovements, ChessPosition position) {
        ChessPosition belowPosition = position.getNextBelowPosition();

        if (isAllowedToTarget(belowPosition)) {
            belowMovements.add(belowPosition);

            if (thereIsAnOpponentPlacedOn(belowPosition))
                return;

            appendNextAvailablePositionBelow(belowMovements, belowPosition);
        }
    }

    private List<ChessPosition> getAllAvailableRightPositions() {
        List<ChessPosition> rightMovements = new ArrayList<>();

        try {
            appendNextAvailablePositionRight(rightMovements, getPosition());

        } catch (ChessException ignored) {}

        return rightMovements;
    }

    private void appendNextAvailablePositionRight(List<ChessPosition> rightMovements, ChessPosition position) {
        ChessPosition rightPosition = position.getNextRightPosition();

        if (isAllowedToTarget(rightPosition)) {
            rightMovements.add(rightPosition);

            if (thereIsAnOpponentPlacedOn(rightPosition))
                return;

            appendNextAvailablePositionRight(rightMovements, rightPosition);
        }
    }

    private List<ChessPosition> getAllAvailableLeftPositions() {
        List<ChessPosition> leftMovements = new ArrayList<>();

        try {
            appendNextAvailablePositionLeft(leftMovements, getPosition());

        } catch (ChessException ignored) {}

        return leftMovements;
    }

    private void appendNextAvailablePositionLeft(List<ChessPosition> leftMovements, ChessPosition position) {
        ChessPosition leftPosition = position.getNextLeftPosition();

        if (isAllowedToTarget(leftPosition)) {
            leftMovements.add(leftPosition);

            if (thereIsAnOpponentPlacedOn(leftPosition))
                return;

            appendNextAvailablePositionLeft(leftMovements, leftPosition);
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
        private Color color;

        private RookBuilder() {}

        public RookBuilder board(ChessBoard board) {
            this.board = board;
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
            return new Rook(board, color);
        }
        
    }

}
