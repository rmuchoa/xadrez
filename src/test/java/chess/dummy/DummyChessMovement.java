package chess.dummy;

import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.dummy.builder.DummyChessMovementBuilder;
import chess.movement.types.MovementType;
import net.bytebuddy.dynamic.scaffold.TypeWriter.FieldPool;

public class DummyChessMovement extends ChessMovement {

    public DummyChessMovement(
        ChessPiece piece,
        ChessPosition source,
        ChessPosition target,
        MovementType type) {

        super(piece, source, target, type);
    }

    public static DummyChessMovementBuilder builder() {
        return DummyChessMovementBuilder.builder();
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return null;
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return null;
    }
}
