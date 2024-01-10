package chess.pieces;

import static chess.movement.types.MovementDirection.NORTHEAST;
import static chess.movement.types.MovementDirection.NORTHWEST;
import static chess.movement.types.MovementDirection.SOUTHEAST;
import static chess.movement.types.MovementDirection.SOUTHWEST;

import chess.ChessBoard;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.Color;
import chess.movement.PawnTakeMovement;
import chess.movement.PawnWalkMovement;

public class Pawn extends ChessPiece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    public void applyAllAvailableMovements() {

        Pawn pawn = this;

        applyAvailableMovements(PawnWalkMovement.checkWalkMovements(pawn));

        applyAvailableMovements(PawnTakeMovement.checkTakeMovement(pawn, NORTHEAST));
        applyAvailableMovements(PawnTakeMovement.checkTakeMovement(pawn, NORTHWEST));
        applyAvailableMovements(PawnTakeMovement.checkTakeMovement(pawn, SOUTHEAST));
        applyAvailableMovements(PawnTakeMovement.checkTakeMovement(pawn, SOUTHWEST));

    }

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        Pawn clonedPawn = new Pawn(getColor());
        clonedPawn.moveCount = getMoveCount();
        clonedPawn.inCheck = isInCheck();
        clonedPawn.inCheckMate = isInCheckMate();
        clonedPawn.placeOnPosition(getPosition().clonePosition(), clonedBoard);

        for (int i = 0; i < availableMovements.size(); i++) {
            ChessMovement movement = availableMovements.get(i);
            clonedPawn.availableMovements.add(movement.cloneMovement(clonedPawn));
        }

        return clonedPawn;
    }

    @Override
    public String toString() {
        return "P";
    }

}
