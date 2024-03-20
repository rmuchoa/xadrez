package chess.movement;

import chess.ChessException;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.movement.types.MovementDirection;
import chess.movement.types.MovementType;
import chess.pieces.Pawn;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PawnWalkMovement extends ChessMovement {

    private static final Integer TWO_SLOTS = 2;
    private static final Integer JUST_ONE_SLOT = 1;

    private final MovementDirection direction;

    private PawnWalkMovement(Pawn pawn, ChessPosition source, ChessPosition target, MovementDirection direction) {
        super(pawn, source, target, MovementType.PAWN_WALK);
        this.direction = direction;
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return buildMovement((Pawn) getPiece(), getTarget(), direction);
    }

    @Override
    protected boolean isAvailableMovement() {
        return isTargetPositionEmpty()
            && isNotAnOverWalkingMovement();
    }

    @Override
    protected void doComposedMove(ChessPiece triggerPiece) {

    }

    @Override
    protected void undoComposedMove(ChessPiece triggerPiece) {

    }

    private boolean isNotAnOverWalkingMovement() {
        return !isAnOverWalkingMovement();
    }

    private boolean isAnOverWalkingMovement() {
        Pawn pawn = (Pawn) getPiece();

        return pawn.hasAlreadyMoved()
            && isTryingToMoveTwoSlots();
    }

    private boolean isTryingToMoveTwoSlots() {
        Pawn pawn = (Pawn) getPiece();

        return switch (pawn.getColor()) {
            case BLACK -> getSource().getMatrixRow() - getTarget().getMatrixRow() == 2;
            case WHITE -> getTarget().getMatrixRow() - getSource().getMatrixRow() == 2;
        };
    }

    public static List<ChessMovement> checkWalkMovements(Pawn pawn) {
        try {
            return buildMovement(pawn)
                .checkMovements(getAvailableMovementSlots(pawn));
        }
        catch (ChessException ex) {
            return Collections.emptyList();
        }
    }

    public static PawnWalkMovement buildMovement(Pawn pawn) {

        MovementDirection direction = getPawnDirection(pawn);

        return buildMovement(pawn, pawn.getPosition(), direction);
    }

    public static PawnWalkMovement buildMovement(Pawn pawn, ChessPosition current, MovementDirection direction) {

        ChessPosition target = getNextPosition(current, direction);

        return new PawnWalkMovement(pawn, pawn.getPosition(), target, direction);
    }

    private static Integer getAvailableMovementSlots(Pawn pawn) {
        return pawn.hasNotMovedYet() ? TWO_SLOTS : JUST_ONE_SLOT;
    }

    private static MovementDirection getPawnDirection(Pawn pawn) {
        return switch (pawn.getColor()) {
            case BLACK -> MovementDirection.SOUTH;
            case WHITE -> MovementDirection.NORTH;
        };
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return new PawnWalkMovement((Pawn) clonedPiece, getSource(), getTarget(), getDirection());
    }

}
