package chess.pieces;

import static chess.movement.types.MovementDirection.EAST;
import static chess.movement.types.MovementDirection.NORTH;
import static chess.movement.types.MovementDirection.SOUTH;
import static chess.movement.types.MovementDirection.WEST;

import chess.ChessBoard;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.Color;
import chess.movement.KnightElMovement;

public class Knight extends ChessPiece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    public void applyAllAvailableMovements() {

        Knight knight = this;

        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, NORTH, EAST));
        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, NORTH, WEST));

        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, SOUTH, EAST));
        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, SOUTH, WEST));

        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, EAST, NORTH));
        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, EAST, SOUTH));

        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, WEST, NORTH));
        applyAvailableMovements(KnightElMovement.checkSingleMovement(knight, WEST, SOUTH));

    }

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        Knight clonedKnight = new Knight(getColor());
        clonedKnight.moveCount = getMoveCount();
        clonedKnight.placeOnPosition(getPosition().clonePosition(), clonedBoard);

        for (int i = 0; i < availableMovements.size(); i++) {
            ChessMovement movement = availableMovements.get(i);
            clonedKnight.availableMovements.add(movement.cloneMovement(clonedKnight));
        }

        return clonedKnight;
    }

    @Override
    public String toString() {
        return "N";
    }

}
