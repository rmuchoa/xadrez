package chess.movement;

import static java.lang.String.format;

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
public class LineMovement extends ChessMovement {

    private final MovementDirection direction;

    private LineMovement(ChessPiece piece, ChessPosition source, ChessPosition target, MovementDirection direction) {
        super(piece, source, target, MovementType.LINE);
        this.direction = direction;
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return buildMovement(getPiece(), getTarget(), direction);
    }

    @Override
    protected void doComposedMove(ChessPiece triggerPiece) {

    }

    @Override
    protected void undoComposedMove(ChessPiece triggerPiece) {

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

    public static LineMovement buildMovement(ChessPiece piece, ChessPosition current, MovementDirection direction) {

        ChessPosition target = getNextPosition(current, direction);

        return new LineMovement(piece, piece.getPosition(), target, direction);
    }

    public static ChessPosition getNextPosition(ChessPosition source, MovementDirection direction) {
        return switch (direction) {
            case NORTH,
                SOUTH,
                EAST,
                WEST -> ChessMovement.getNextPosition(source, direction);
            case NORTHEAST,
                NORTHWEST,
                SOUTHEAST,
                SOUTHWEST -> throw new ChessException(format("Invalid %s direction for LineMovement", direction));
        };
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return new LineMovement(clonedPiece, getSource(), getTarget(), getDirection());
    }

}
