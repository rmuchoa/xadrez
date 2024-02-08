package chess.pieces;

import static chess.movement.types.MovementDirection.EAST;
import static chess.movement.types.MovementDirection.NORTH;
import static chess.movement.types.MovementDirection.SOUTH;
import static chess.movement.types.MovementDirection.WEST;

import chess.ChessBoard;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.Color;
import chess.movement.LineMovement;

public class Rook extends ChessPiece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    public void applyAllAvailableMovements() {

        Rook rook = this;

        applyAvailableMovements(LineMovement.checkMovements(rook, NORTH));
        applyAvailableMovements(LineMovement.checkMovements(rook, SOUTH));
        applyAvailableMovements(LineMovement.checkMovements(rook, EAST));
        applyAvailableMovements(LineMovement.checkMovements(rook, WEST));

    }

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        Rook clonedRook = new Rook(getColor());
        clonedRook.moveCount = getMoveCount();
        clonedRook.placeOnPosition(getPosition().clonePosition(), clonedBoard);

        for (int i = 0; i < availableMovements.size(); i++) {
            ChessMovement movement = availableMovements.get(i);
            clonedRook.availableMovements.add(movement.cloneMovement(clonedRook));
        }

        return clonedRook;
    }

    @Override
    public String toString() {
        return "R";
    }

}
