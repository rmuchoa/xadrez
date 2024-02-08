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
import chess.ChessMovement;
import chess.ChessPiece;
import chess.Color;
import chess.movement.DiagonalMovement;
import chess.movement.LineMovement;

public class Queen extends ChessPiece {

    public Queen(Color color) {
        super(color);
    }

    @Override
    public void applyAllAvailableMovements() {

        Queen queen = this;

        applyAvailableMovements(LineMovement.checkMovements(queen, NORTH));
        applyAvailableMovements(LineMovement.checkMovements(queen, SOUTH));
        applyAvailableMovements(LineMovement.checkMovements(queen, EAST));
        applyAvailableMovements(LineMovement.checkMovements(queen, WEST));

        applyAvailableMovements(DiagonalMovement.checkMovements(queen, NORTHEAST));
        applyAvailableMovements(DiagonalMovement.checkMovements(queen, NORTHWEST));
        applyAvailableMovements(DiagonalMovement.checkMovements(queen, SOUTHEAST));
        applyAvailableMovements(DiagonalMovement.checkMovements(queen, SOUTHWEST));

    }

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        Queen clonedQueen = new Queen(getColor());
        clonedQueen.moveCount = getMoveCount();
        clonedQueen.placeOnPosition(getPosition().clonePosition(), clonedBoard);

        for (int i = 0; i < availableMovements.size(); i++) {
            ChessMovement movement = availableMovements.get(i);
            clonedQueen.availableMovements.add(movement.cloneMovement(clonedQueen));
        }

        return clonedQueen;
    }

    @Override
    public String toString() {
        return "Q";
    }

}
