package chess.dummy.builder;

import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.dummy.DummyChessMovement;
import chess.movement.types.MovementType;

public class DummyChessMovementBuilder {

    private ChessPiece piece;
    private ChessPosition source;
    private ChessPosition target;
    private MovementType movementType;
    private ChessMovement nextMovement;
    private ChessMovement clonedMovement;

    private DummyChessMovementBuilder() {}

    public static DummyChessMovementBuilder builder() {
        return new DummyChessMovementBuilder();
    }

    public DummyChessMovementBuilder piece(ChessPiece piece) {
        this.piece = piece;
        return this;
    }

    public DummyChessMovementBuilder source(ChessPosition source) {
        this.source = source;
        return this;
    }

    public DummyChessMovementBuilder target(ChessPosition target) {
        this.target = target;
        return this;
    }

    public DummyChessMovementBuilder line() {
        return movementType(MovementType.LINE);
    }

    public DummyChessMovementBuilder diagonal() {
        return movementType(MovementType.DIAGONAL);
    }

    public DummyChessMovementBuilder castling() {
        return movementType(MovementType.CASTLING);
    }

    public DummyChessMovementBuilder knightEl() {
        return movementType(MovementType.KNIGHT_EL);
    }

    public DummyChessMovementBuilder pawnWalk() {
        return movementType(MovementType.PAWN_WALK);
    }

    public DummyChessMovementBuilder pawnTake() {
        return movementType(MovementType.PAWN_TAKE);
    }

    public DummyChessMovementBuilder enPassant() {
        return movementType(MovementType.EN_PASSANT);
    }

    public DummyChessMovementBuilder movementType(MovementType movementType) {
        this.movementType = movementType;
        return this;
    }

    public DummyChessMovementBuilder nextMovement(ChessMovement nextMovement) {
        this.nextMovement = nextMovement;
        return this;
    }

    public DummyChessMovementBuilder clonedMovement(ChessMovement clonedMovement) {
        this.clonedMovement = clonedMovement;
        return this;
    }

    public DummyChessMovement build() {
        return new DummyChessMovement(
            piece,
            source,
            target,
            movementType,
            nextMovement,
            clonedMovement);
    }

}
