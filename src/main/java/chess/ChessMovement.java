package chess;

import static chess.ChessMovement.MovementDurationType.LIMITED;
import static chess.ChessMovement.MovementDurationType.All_EXTENSION;
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

    protected final ChessBoard board;
    private final ChessPiece piece;
    private final ChessPosition source;
    private final ChessPosition target;
    private final MovementType type;

    public ChessMovement(ChessPiece piece, ChessPosition source, ChessPosition target, MovementType type) {
        this.piece = piece;
        this.source = source;
        this.target = target;
        this.board = piece.getBoard();
        this.type = type;
    }

    public List<ChessMovement> checkMovements() {
        return checkMovements(this, All_EXTENSION, null);
    }

    public List<ChessMovement> checkMovements(Integer movementSlots) {
        return checkMovements(this, LIMITED, movementSlots);
    }

    public List<ChessMovement> checkMovements(ChessMovement movement, MovementDurationType duration, Integer remainingMovements) {
        List<ChessMovement> availableMovements = new ArrayList<>();

        if (movement == null || movement.isNotAvailableMovement())
            return Collections.emptyList();

        availableMovements.add(movement);

        if (movement.hasOpponentOnTargetPosition())
            return availableMovements;


        if (duration.allowMoveFreely() || duration.allowMoreMovements(remainingMovements)) {

            try {
                ChessMovement nextMovement = movement.buildNextMovement();

                Integer decreasedMovementCounter = decreaseMovementDistance(remainingMovements);
                List<ChessMovement> allowedNextMovements = checkMovements(nextMovement, duration, decreasedMovementCounter);

                availableMovements.addAll(allowedNextMovements);

            } catch (ChessException ignored) {}

        }

        return availableMovements;
    }

    protected Integer decreaseMovementDistance(Integer remainingSlots) {

        if (remainingSlots == null)
            return null;

        if (remainingSlots > 0)
            return --remainingSlots;

        return remainingSlots;
    }

    protected abstract ChessMovement buildNextMovement();

    protected boolean isNotAvailableMovement() {
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
        return isTargetPositionOccupied()
            && thereIsAnOpponentOnTargetPosition();
    }

    protected boolean doesExistsTargetPosition() {
        return board.doesExists(target);
    }

    protected boolean thereIsAnOpponentOnTargetPosition() {
        ChessPiece sourcePiece = board.getPiecePlacedOn(source);
        ChessPiece targetPiece = board.getPiecePlacedOn(target);
        return sourcePiece.isOpponentOf(targetPiece);
    }

    public ChessPiece doMove() {
        ChessPiece movingPiece = board.removePieceFrom(source);
        ChessPiece capturedPiece = board.removePieceFrom(target);

        board.placePieceOn(target, movingPiece);
        movingPiece.increaseMoveCount();

        doComposedMove(movingPiece);

        board.getAllPlacedPieces()
            .forEach(ChessPiece::setUpAvailableMovements);

        return capturedPiece;
    }

    protected abstract void doComposedMove(ChessPiece triggerPiece);

    public void undoMove(ChessPiece capturedPiece) {
        ChessPiece movingPiece = board.removePieceFrom(target);
        board.placePieceOn(source, movingPiece);
        movingPiece.decreaseMoveCount();

        if (capturedPiece != null)
            board.placePieceOn(target, capturedPiece);

        undoComposedMove(movingPiece);

        board.getAllPlacedPieces()
            .forEach(ChessPiece::setUpAvailableMovements);
    }

    protected abstract void undoComposedMove(ChessPiece triggerPiece);

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

    public enum MovementDurationType {

        All_EXTENSION(true),
        LIMITED(false);

        private final boolean freeWayToMove;

        MovementDurationType(boolean freeWayToMove) {
            this.freeWayToMove = freeWayToMove;
        }

        public boolean allowMoveFreely() {
            return freeWayToMove;
        }

        public boolean allowMoreMovements(int remainingSlots) {
            if (allowMoveFreely())
                return true;

            return ONE_SLOTS < remainingSlots;
        }
    }

}
