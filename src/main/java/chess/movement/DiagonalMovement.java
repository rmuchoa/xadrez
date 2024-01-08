package chess.movement;

import chess.ChessException;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.movement.types.MovementDirection;
import chess.movement.types.MovementType;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class DiagonalMovement extends ChessMovement {

    private final MovementDirection direction;

    private DiagonalMovement(ChessPiece piece, ChessPosition source, ChessPosition target, MovementDirection direction) {
        super(piece, source, target, MovementType.DIAGONAL);
        this.direction = direction;
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return buildMovement(getPiece(), getTarget(), direction);
    }

    public static List<ChessMovement> checkSingleMovement(ChessPiece piece, MovementDirection direction) {
        try {
            return buildMovement(piece, piece.getPosition(), direction)
                .checkMovements(1);
        }
        catch (ChessException ex) {
            return Collections.emptyList();
        }
    }

    public static List<ChessMovement> checkMovements(ChessPiece piece, MovementDirection direction) {
        try {
            return buildMovement(piece, piece.getPosition(), direction)
                .checkMovements();
        }
        catch (ChessException ex) {
            return Collections.emptyList();
        }
    }

    public static DiagonalMovement buildMovement(ChessPiece piece, ChessPosition current, MovementDirection direction) {

        ChessPosition target = getNextPosition(current, direction);

        return new DiagonalMovement(piece, piece.getPosition(), target, direction);
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return new DiagonalMovement(clonedPiece, getSource(), getTarget(), getDirection());
    }

}
