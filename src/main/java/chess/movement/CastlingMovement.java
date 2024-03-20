package chess.movement;

import static chess.ChessPosition.buildPositionFor;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.movement.types.MovementDirection;
import chess.movement.types.MovementType;
import chess.pieces.Empty;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CastlingMovement extends ChessMovement {

    private static final int TWO_SLOTS = 2;
    private static final int THREE_SLOTS = 3;

    private final MovementDirection direction;
    private final CastlingMovement rookMovement;

    public CastlingMovement(ChessPiece piece, ChessPosition source, ChessPosition target, MovementDirection direction) {
        this(piece, source, target, direction, null);
    }

    public CastlingMovement(ChessPiece piece, ChessPosition source, ChessPosition target, MovementDirection direction, CastlingMovement rookMovement) {
        super(piece, source, target, MovementType.CASTLING);
        this.direction = direction;
        this.rookMovement = rookMovement;
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return null;
    }

    @Override
    protected boolean isAvailableMovement() {
        if (getPiece() instanceof  King king)
            return isAvailableKingMovement(king);

        if (getPiece() instanceof Rook rook)
            return isAvailableRookMovement(rook);

        if (getPiece() instanceof Empty)
            return isMiddleWayIsFreelyOpen();

        return false;
    }

    @Override
    protected void doComposedMove(ChessPiece triggerPiece) {
        if (triggerPiece instanceof King) {
            executeRookCastling();
        }
    }

    @Override
    protected void undoComposedMove(ChessPiece triggerPiece) {
        if (triggerPiece instanceof King) {
            undoRookCastling();
        }
    }

    private boolean isAvailableKingMovement(King king) {
        return king.hasNotMovedYet() && hasKingFreeWayToTarget(king)
            && rookMovement != null && rookMovement.isAvailableMovement();
    }

    private boolean isAvailableRookMovement(Rook rook) {
        return rook.hasNotMovedYet()
            && hasRookFreeWayToTarget(rook);
    }

    private boolean isMiddleWayIsFreelyOpen() {
        return isTargetPositionEmpty();
    }

    private boolean hasKingFreeWayToTarget(King king) {
        return switch (direction) {
            case EAST -> hasFreeWayToEastDuring(TWO_SLOTS, king.getPosition());
            case WEST -> hasFreeWayToWestDuring(TWO_SLOTS, king.getPosition());
            case null, default -> false;
        };
    }

    private boolean hasRookFreeWayToTarget(Rook rook) {
        return switch (direction) {
            case EAST -> hasFreeWayToWestDuring(TWO_SLOTS, rook.getPosition());
            case WEST -> hasFreeWayToEastDuring(THREE_SLOTS, rook.getPosition());
            case null, default -> false;
        };
    }

    private boolean hasFreeWayToEastDuring(int movementSlots, ChessPosition source) {

        if (movementSlots == 0)
            return true;

        ChessPosition target = buildPositionFor(source.getNextEastChessColumn(), source.getSameChessRow());

        Empty empty = new Empty();
        empty.placeOnPosition(source, getBoard());
        CastlingMovement middleWayMovement = new CastlingMovement(empty, source, target, direction);

        return middleWayMovement.isAvailableMovement()
            && hasFreeWayToEastDuring(--movementSlots, target);
    }

    private boolean hasFreeWayToWestDuring(int movementSlots, ChessPosition source) {

        if (movementSlots == 0)
            return true;

        ChessPosition target = buildPositionFor(source.getNextWestChessColumn(), source.getSameChessRow());

        Empty empty = new Empty();
        empty.placeOnPosition(source, getBoard());
        CastlingMovement middleWayMovement = new CastlingMovement(empty, source, target, direction);

        return middleWayMovement.isAvailableMovement()
            && hasFreeWayToWestDuring(--movementSlots, target);
    }

    public static List<ChessMovement> checkKingSideMovement(King king) {
        try {
            return buildMovement(king, MovementDirection.EAST)
                .checkMovements(1);
        }
        catch (ChessException ex) {
            return Collections.emptyList();
        }
    }

    public static List<ChessMovement> checkQueenSideMovement(King king) {
        try {
            return buildMovement(king, MovementDirection.WEST)
                .checkMovements(1);
        }
        catch (ChessException ex) {
            return Collections.emptyList();
        }
    }

    public static CastlingMovement buildMovement(King king, MovementDirection direction) {

        ChessPosition target = buildCastlingKingTargetPosition(king.getPosition(), direction);

        CastlingMovement rookMovement = buildRookCastlingMovement(king, direction);

        return new CastlingMovement(king, king.getPosition(), target, direction, rookMovement);
    }

    private static ChessPosition buildCastlingKingTargetPosition(ChessPosition kingPosition, MovementDirection direction) {
        return switch (direction) {
            case EAST -> buildPositionFor(kingPosition.getTwoBesideEastChessColumn(), kingPosition.getSameChessRow());
            case WEST -> buildPositionFor(kingPosition.getTwoBesideWestChessColumn(), kingPosition.getSameChessRow());
            case null, default -> null;
        };
    }

    private static CastlingMovement buildRookCastlingMovement(King king, MovementDirection direction) {

        ChessBoard board = king.getBoard();
        ChessPosition rookPosition = buildCastlingRookPosition(king.getPosition(), direction);
        ChessPiece supposeToBeRook = board.getPiecePlacedOn(rookPosition);

        if (board.isBoardPositionOccupied(rookPosition) && supposeToBeRook instanceof Rook rook) {

            ChessPosition rookTargetPosition = buildCastlingRookTargetPosition(rookPosition, direction);
            return new CastlingMovement(rook, rookPosition, rookTargetPosition, direction);
        } else {

            return null;
        }
    }

    private static ChessPosition buildCastlingRookPosition(ChessPosition kingPosition, MovementDirection direction) {
        return switch (direction) {
            case EAST -> buildPositionFor(kingPosition.getThreeBesideEastChessColumn(), kingPosition.getSameChessRow());
            case WEST -> buildPositionFor(kingPosition.getFourBesideWestChessColumn(), kingPosition.getSameChessRow());
            case null, default -> null;
        };
    }

    private static ChessPosition buildCastlingRookTargetPosition(ChessPosition rookPosition, MovementDirection direction) {
        return switch (direction) {
            case EAST -> buildPositionFor(rookPosition.getTwoBesideWestChessColumn(), rookPosition.getSameChessRow());
            case WEST -> buildPositionFor(rookPosition.getThreeBesideEastChessColumn(), rookPosition.getSameChessRow());
            case null, default -> null;
        };
    }

    private void executeRookCastling() {
        ChessMovement rookMovement = getRookMovement();

        ChessPiece rook = board.removePieceFrom(rookMovement.getSource());
        board.placePieceOn(rookMovement.getTarget(), rook);
        rook.increaseMoveCount();
    }

    private void undoRookCastling() {
        ChessMovement rookMovement = getRookMovement();

        ChessPiece rook = board.removePieceFrom(rookMovement.getTarget());
        board.placePieceOn(rookMovement.getSource(), rook);
        rook.increaseMoveCount();
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return new CastlingMovement(clonedPiece, getSource(), getTarget(), getDirection(), getRookMovement());
    }

}
