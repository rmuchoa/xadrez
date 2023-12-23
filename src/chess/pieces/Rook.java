package chess.pieces;

import boardgame.Board;
import boardgame.BoardPosition;
import chess.ChessException;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public List<BoardPosition> getAllAvailableTargetPositions() {
        List<BoardPosition> possibleMovements = new ArrayList<>();

        possibleMovements.addAll(getAllAvailableAbovePositions());
        possibleMovements.addAll(getAllAvailableBelowPositions());
        possibleMovements.addAll(getAllAvailableRightPositions());
        possibleMovements.addAll(getAllAvailableLeftPositions());

        return possibleMovements;
    }

    private List<BoardPosition> getAllAvailableAbovePositions() {
        ChessPosition current = ChessPosition.fromPosition(getPosition());
        ChessPosition abovePosition = current.getNextAbovePosition();

        List<BoardPosition> aboveMovements = new ArrayList<>();
        while (true) {
            if (isAllowedToTarget(abovePosition)) {
                aboveMovements.add(abovePosition.toBoardPosition());

                if (thereIsAnOpponentPiecePlacedOn(abovePosition))
                    break;

            } else {
                break;
            }

            try {
                abovePosition = abovePosition.getNextAbovePosition();
            } catch (ChessException ex) {
                return aboveMovements;
            }
        }

        return aboveMovements;
    }

    private List<BoardPosition> getAllAvailableBelowPositions() {
        ChessPosition current = ChessPosition.fromPosition(getPosition());
        ChessPosition belowPosition = current.getNextBelowPosition();

        List<BoardPosition> belowMovements = new ArrayList<>();
        while (true) {
            if (isAllowedToTarget(belowPosition)) {
                belowMovements.add(belowPosition.toBoardPosition());

                if (thereIsAnOpponentPiecePlacedOn(belowPosition))
                    break;

            } else {
                break;
            }

            try {
                belowPosition = belowPosition.getNextBelowPosition();
            } catch (ChessException ex) {
                return belowMovements;
            }
        }

        return belowMovements;
    }

    private List<BoardPosition> getAllAvailableRightPositions() {
        ChessPosition current = ChessPosition.fromPosition(getPosition());
        ChessPosition rightPosition = current.getNextRightPosition();

        List<BoardPosition> rightMovements = new ArrayList<>();
        while (true) {
            if (isAllowedToTarget(rightPosition)) {
                rightMovements.add(rightPosition.toBoardPosition());

                if (thereIsAnOpponentPiecePlacedOn(rightPosition))
                    break;

            } else {
                break;
            }

            try {
                rightPosition = rightPosition.getNextRightPosition();
            } catch (ChessException ex) {
                return rightMovements;
            }
        }

        return rightMovements;
    }

    private List<BoardPosition> getAllAvailableLeftPositions() {
        ChessPosition current = ChessPosition.fromPosition(getPosition());
        ChessPosition leftPosition = current.getNextLeftPosition();

        List<BoardPosition> leftMovements = new ArrayList<>();
        while (true) {
            if (isAllowedToTarget(leftPosition)) {
                leftMovements.add(leftPosition.toBoardPosition());

                if (thereIsAnOpponentPiecePlacedOn(leftPosition))
                    break;

            } else {
                break;
            }

            try {
                leftPosition = leftPosition.getNextLeftPosition();
            } catch (ChessException ex) {
                return leftMovements;
            }
        }

        return leftMovements;
    }

    @Override
    public String toString() {
        return "R";
    }

}
