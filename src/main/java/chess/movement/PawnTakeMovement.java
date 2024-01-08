package chess.movement;

import static chess.movement.types.MovementDirection.NORTHEAST;
import static chess.movement.types.MovementDirection.NORTHWEST;
import static chess.movement.types.MovementDirection.SOUTHEAST;
import static chess.movement.types.MovementDirection.SOUTHWEST;

import chess.ChessException;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.movement.types.MovementDirection;
import chess.movement.types.MovementType;
import chess.pieces.Pawn;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PawnTakeMovement extends ChessMovement {

    private static final List<MovementDirection> BLACK_ALLOWED_DIRECTIONS = Arrays.asList(SOUTHEAST, SOUTHWEST);
    private static final List<MovementDirection> WHITE_ALLOWED_DIRECTIONS = Arrays.asList(NORTHEAST, NORTHWEST);

    private final MovementDirection direction;

    private PawnTakeMovement(Pawn pawn, ChessPosition source, ChessPosition target, MovementDirection direction) {
        super(pawn, source, target, MovementType.PAWN_TAKE);
        this.direction = direction;
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return null;
    }

    @Override
    protected boolean isAvailableMovement() {
        return isTargetPositionOccupied()
            && hasOpponentOnTargetPosition();
    }

    public static List<ChessMovement> checkTakeMovement(Pawn pawn, MovementDirection direction) {
        try {
            if (isNotAllowedDirection(pawn, direction))
                return Collections.emptyList();

            return buildMovement(pawn, direction)
                .checkMovements(1);
        }
        catch (ChessException ex) {
            return Collections.emptyList();
        }
    }

    public static PawnTakeMovement buildMovement(Pawn pawn, MovementDirection direction) {

        ChessPosition target = getNextPosition(pawn.getPosition(), direction);

        return new PawnTakeMovement(pawn, pawn.getPosition(), target, direction);
    }

    private static Boolean isNotAllowedDirection(Pawn pawn, MovementDirection direction) {
        return !isAllowedDirection(pawn, direction);
    }

    private static Boolean isAllowedDirection(Pawn pawn, MovementDirection direction) {
        return switch (pawn.getColor()) {
            case BLACK -> BLACK_ALLOWED_DIRECTIONS.contains(direction);
            case WHITE -> WHITE_ALLOWED_DIRECTIONS.contains(direction);
        };
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return new PawnTakeMovement((Pawn) clonedPiece, getSource(), getTarget(), getDirection());
    }

}
