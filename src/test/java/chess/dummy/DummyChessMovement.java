package chess.dummy;

import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.dummy.builder.DummyChessMovementBuilder;
import chess.movement.types.MovementType;

public class DummyChessMovement extends ChessMovement {

    public DummyChessMovement(
        ChessPiece piece,
        ChessPosition source,
        ChessPosition target,
        MovementType type,
        ChessMovement nextMovement,
        ChessMovement clonedMovement,
        Boolean composedMoveDone,
        Boolean composedMoveUndone) {

        super(piece, source, target, type);
        this.nextMovement = nextMovement;
        this.clonedMovement = clonedMovement;
        this.composedMoveDone = composedMoveDone;
        this.composedMoveUndone = composedMoveUndone;
    }

    private final ChessMovement nextMovement;
    private final ChessMovement clonedMovement;

    private Boolean composedMoveDone;
    private Boolean composedMoveUndone;

    public Boolean isComposedMoveDone() {
        return composedMoveDone;
    }

    public Boolean isComposedMoveUndone() {
        return composedMoveUndone;
    }

    public static DummyChessMovementBuilder builder() {
        return DummyChessMovementBuilder.builder();
    }

    @Override
    protected ChessMovement buildNextMovement() {
        return nextMovement;
    }

    @Override
    protected void doComposedMove(ChessPiece triggerPiece) {
        composedMoveDone = true;
    }

    @Override
    protected void undoComposedMove(ChessPiece triggerPiece) {
        composedMoveUndone = true;
    }

    @Override
    public ChessMovement cloneMovement(ChessPiece clonedPiece) {
        return clonedMovement;
    }

}
