package chess.dummy;

import chess.ChessBoard;
import chess.ChessMovement;
import chess.ChessPosition;
import chess.Color;
import chess.dummy.builder.DummyPawnBuilder;
import chess.pieces.Pawn;
import java.util.List;

public class DummyPawn extends Pawn {

    private List<ChessMovement> allAvailableMovements;

    public DummyPawn(Color color, ChessBoard board, ChessPosition position, List<ChessMovement> allAvailableMovements) {
        super(color);
        this.allAvailableMovements = allAvailableMovements;
        placeOnPosition(position, board);
        applyMatch();
    }

    @Override
    public void applyAllAvailableMovements() {
        availableMovements.addAll(allAvailableMovements);
    }

    public static DummyPawnBuilder builder() {
        return DummyPawnBuilder.builder();
    }

}
