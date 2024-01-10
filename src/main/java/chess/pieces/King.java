package chess.pieces;

import static chess.movement.types.MovementDirection.EAST;
import static chess.movement.types.MovementDirection.NORTH;
import static chess.movement.types.MovementDirection.NORTHEAST;
import static chess.movement.types.MovementDirection.NORTHWEST;
import static chess.movement.types.MovementDirection.SOUTH;
import static chess.movement.types.MovementDirection.SOUTHEAST;
import static chess.movement.types.MovementDirection.SOUTHWEST;
import static chess.movement.types.MovementDirection.WEST;

import chess.ChessBoard;
import chess.ChessMatch;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.Color;
import chess.movement.CastlingMovement;
import chess.movement.DiagonalMovement;
import chess.movement.LineMovement;

public class King extends ChessPiece {

    public King(Color color) {
        super(color);
    }

    @Override
    public void applyAllAvailableMovements() {

        King king = this;

        king.applyAvailableMovements(LineMovement.checkSingleMovement(king, NORTH));
        king.applyAvailableMovements(LineMovement.checkSingleMovement(king, SOUTH));
        king.applyAvailableMovements(LineMovement.checkSingleMovement(king, EAST));
        king.applyAvailableMovements(LineMovement.checkSingleMovement(king, WEST));

        king.applyAvailableMovements(DiagonalMovement.checkSingleMovement(king, NORTHEAST));
        king.applyAvailableMovements(DiagonalMovement.checkSingleMovement(king, NORTHWEST));
        king.applyAvailableMovements(DiagonalMovement.checkSingleMovement(king, SOUTHEAST));
        king.applyAvailableMovements(DiagonalMovement.checkSingleMovement(king, SOUTHWEST));

        king.applyAvailableMovements(CastlingMovement.checkKingSideMovement(king));
        king.applyAvailableMovements(CastlingMovement.checkQueenSideMovement(king));

    }

    public boolean canBeSavedDuringCheckBy(ChessPiece piece) {
        return piece.getAvailableMovements()
            .stream()
            .anyMatch(movement -> {
                boolean canBeSaved = false;
                ChessMatch match = getMatch().cloneMatch();
                ChessPiece captured = match.makeMovement(movement);
                King opponentKing = match.getOpponentKing();

                if (match.cannotDetectCheckScenario(opponentKing))
                    canBeSaved = true;

                match.undoMovement(movement, captured);

                return canBeSaved;
            });
    }

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        King clonedKing = new King(getColor());
        clonedKing.moveCount = getMoveCount();
        clonedKing.inCheck = isInCheck();
        clonedKing.inCheckMate = isInCheckMate();
        clonedKing.placeOnPosition(getPosition().clonePosition(), clonedBoard);

        for (int i = 0; i < availableMovements.size(); i++) {
            ChessMovement movement = availableMovements.get(i);
            clonedKing.availableMovements.add(movement.cloneMovement(clonedKing));
        }

        return clonedKing;
    }

    @Override
    public String toString() {
        return "K";
    }

}
