package chess.movement;

import static chess.ChessPosition.buildPositionFor;

import chess.ChessException;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.movement.types.MovementDirection;
import chess.movement.types.MovementType;
import chess.pieces.Knight;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class KnightElMovement extends ChessMovement {

    private MovementDirection primary;
    private MovementDirection secondary;

    public KnightElMovement(Knight knight, ChessPosition source, ChessPosition target, MovementDirection primary, MovementDirection secondary) {
        super(knight, source, target, MovementType.KNIGHT_EL);
        this.primary = primary;
        this.secondary = secondary;
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return null;
    }

    public static List<ChessMovement> checkSingleMovement(Knight knight, MovementDirection primary, MovementDirection secondary) {
        try {
            return buildMovement(knight, primary, secondary)
                .checkMovements(1);
        }
        catch (ChessException ex) {
            return Collections.emptyList();
        }
    }

    public static KnightElMovement buildMovement(Knight knight, MovementDirection primary, MovementDirection secondary) {

        ChessPosition target = buildElTargetPosition(knight.getPosition(), primary, secondary);

        return new KnightElMovement(knight, knight.getPosition(), target, primary, secondary);
    }

    private static ChessPosition buildElTargetPosition(ChessPosition source, MovementDirection primary, MovementDirection secondary) {
        return switch (primary) {
            case NORTH -> getNorthElTargetPosition(source, secondary);
            case SOUTH -> getSouthElTargetPosition(source, secondary);
            case EAST -> getEastElTargetPosition(source, secondary);
            case WEST -> getWestElTargetPosition(source, secondary);
            case null, default -> null;
        };
    }

    private static ChessPosition getNorthElTargetPosition(ChessPosition source, MovementDirection secondary) {
        return switch (secondary) {
            case EAST -> buildPositionFor(source.getNextEastChessColumn(), source.getTwoAheadNorthChessRow());
            case WEST -> buildPositionFor(source.getNextWestChessColumn(), source.getTwoAheadNorthChessRow());
            case null, default -> null;
        };
    }

    private static ChessPosition getSouthElTargetPosition(ChessPosition source, MovementDirection secondary) {
        return switch (secondary) {
            case EAST -> buildPositionFor(source.getNextEastChessColumn(), source.getTwoBehindSouthChessRow());
            case WEST -> buildPositionFor(source.getNextWestChessColumn(), source.getTwoBehindSouthChessRow());
            case null, default -> null;
        };
    }

    private static ChessPosition getEastElTargetPosition(ChessPosition source, MovementDirection secondary) {
        return switch (secondary) {
            case NORTH -> buildPositionFor(source.getTwoBesideEastChessColumn(), source.getNextNorthChessRow());
            case SOUTH -> buildPositionFor(source.getTwoBesideEastChessColumn(), source.getNextSouthChessRow());
            case null, default -> null;
        };
    }

    private static ChessPosition getWestElTargetPosition(ChessPosition source, MovementDirection secondary) {
        return switch (secondary) {
            case NORTH -> buildPositionFor(source.getTwoBesideWestChessColumn(), source.getNextNorthChessRow());
            case SOUTH -> buildPositionFor(source.getTwoBesideWestChessColumn(), source.getNextSouthChessRow());
            case null, default -> null;
        };
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return new KnightElMovement((Knight) clonedPiece, getSource(), getTarget(), getPrimary(), getSecondary());
    }
}
