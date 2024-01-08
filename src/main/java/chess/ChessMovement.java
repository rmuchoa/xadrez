package chess;

import static chess.ChessMovement.AllowedSlotsType.LIMITED_SLOTS;
import static chess.ChessMovement.AllowedSlotsType.TILL_FINAL_SLOTS;
import static chess.ChessPosition.buildPositionFor;

import chess.movement.types.MovementDirection;
import chess.movement.types.MovementType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class ChessMovement {

    private static final int ONE_SLOTS = 1;

    private final ChessPiece piece;
    private final ChessPosition source;
    private final ChessPosition target;
    private final ChessBoard board;
    private final MovementType type;

    public ChessMovement(ChessPiece piece, ChessPosition source, ChessPosition target, MovementType type) {
        this.piece = piece;
        this.source = source;
        this.target = target;
        this.board = piece.getBoard();
        this.type = type;
    }

    public List<ChessMovement> checkMovements() {
        return checkMovements(this, TILL_FINAL_SLOTS, null);
    }

    public List<ChessMovement> checkMovements(Integer movementSlots) {
        return checkMovements(this, LIMITED_SLOTS, movementSlots);
    }

    public List<ChessMovement> checkMovements(ChessMovement movement, AllowedSlotsType slotsType, Integer allowedSlots) {
        List<ChessMovement> availableMovements = new ArrayList<>();

        if (movement == null || movement.isNotAvailableMovement())
            return Collections.emptyList();

        availableMovements.add(movement);

        if (movement.hasOpponentOnTargetPosition())
            return availableMovements;


        if (isAllowedToMoveSlotsFreely(slotsType) || hasMoreAllowedSlots(slotsType, allowedSlots)) {

            try {
                ChessMovement nextMovement = movement.buildNextMovement();
                List<ChessMovement> allowedNextMovements = checkMovements(nextMovement, slotsType,
                    resolveAllowedSlots(allowedSlots));
                availableMovements.addAll(allowedNextMovements);
            } catch (ChessException ignored) {}

        }

        return availableMovements;
    }

    private Integer resolveAllowedSlots(Integer allowedSlots) {
        if (allowedSlots == null)
            return null;

        return --allowedSlots;
    }

    private boolean isAllowedToMoveSlotsFreely(AllowedSlotsType slotsType) {
        return TILL_FINAL_SLOTS.equals(slotsType);
    }

    private Boolean hasMoreAllowedSlots(AllowedSlotsType slotsType, Integer allowedSlots) {
        return LIMITED_SLOTS.equals(slotsType) && allowedSlots != null && ONE_SLOTS < allowedSlots;
    }

    protected abstract ChessMovement buildNextMovement();

    private boolean isNotAvailableMovement() {
        return !isAvailableMovement();
    }

    protected boolean isAvailableMovement() {
        return isTargetPositionEmpty()
            || hasOpponentOnTargetPosition();
    }

    protected boolean isTargetPositionEmpty() {
        return doesExistsTargetPosition()
            && board.isBoardPositionEmpty(target);
    }

    protected boolean isTargetPositionOccupied() {
        return doesExistsTargetPosition()
            && board.isBoardPositionOccupied(target);
    }

    protected boolean hasOpponentOnTargetPosition() {
        return doesExistsTargetPosition()
            && board.isBoardPositionOccupied(target)
            && thereIsAnOpponentOnTargetPosition();
    }

    protected boolean doesExistsTargetPosition() {
        return board.doesExists(target);
    }

    private boolean thereIsAnOpponentOnTargetPosition() {
        ChessPiece piece = board.getPiecePlacedOn(source);
        ChessPiece otherPiece = board.getPiecePlacedOn(target);
        return piece.isOpponentOf(otherPiece);
    }

    public static ChessPosition getNextPosition(ChessPosition source, MovementDirection direction) {
        return switch (direction) {
            case NORTH -> buildPositionFor(source.getSameChessColumn(), source.getNextNorthChessRow());
            case SOUTH -> buildPositionFor(source.getSameChessColumn(), source.getNextSouthChessRow());
            case EAST -> buildPositionFor(source.getNextEastChessColumn(), source.getSameChessRow());
            case WEST -> buildPositionFor(source.getNextWestChessColumn(), source.getSameChessRow());
            case NORTHEAST -> buildPositionFor(source.getNextEastChessColumn(), source.getNextNorthChessRow());
            case NORTHWEST -> buildPositionFor(source.getNextWestChessColumn(), source.getNextNorthChessRow());
            case SOUTHEAST -> buildPositionFor(source.getNextEastChessColumn(), source.getNextSouthChessRow());
            case SOUTHWEST -> buildPositionFor(source.getNextWestChessColumn(), source.getNextSouthChessRow());
        };
    }

    public abstract ChessMovement cloneMovement(ChessPiece clonedPiece);

    public enum AllowedSlotsType {

        TILL_FINAL_SLOTS,
        LIMITED_SLOTS

    }

}
