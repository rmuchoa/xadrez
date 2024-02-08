package chess.pieces;

import static chess.movement.types.MovementDirection.NORTHEAST;
import static chess.movement.types.MovementDirection.NORTHWEST;
import static chess.movement.types.MovementDirection.SOUTHEAST;
import static chess.movement.types.MovementDirection.SOUTHWEST;

import chess.ChessBoard;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.Color;
import chess.movement.DiagonalMovement;

public class Bishop extends ChessPiece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    public void applyAllAvailableMovements() {

        Bishop bishop = this;

        applyAvailableMovements(DiagonalMovement.checkMovements(bishop, NORTHEAST));
        applyAvailableMovements(DiagonalMovement.checkMovements(bishop, NORTHWEST));
        applyAvailableMovements(DiagonalMovement.checkMovements(bishop, SOUTHEAST));
        applyAvailableMovements(DiagonalMovement.checkMovements(bishop, SOUTHWEST));

    }

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        Bishop clonedBishop = new Bishop(getColor());
        clonedBishop.moveCount = getMoveCount();
        clonedBishop.placeOnPosition(getPosition().clonePosition(), clonedBoard);

        for (int i = 0; i < availableMovements.size(); i++) {
            ChessMovement movement = availableMovements.get(i);
            clonedBishop.availableMovements.add(movement.cloneMovement(clonedBishop));
        }

        return clonedBishop;
    }

    @Override
    public String toString() {
        return "B";
    }

}
