package chess.dummy.builder;

import chess.ChessBoard;
import chess.ChessMovement;
import chess.ChessPosition;
import chess.Color;
import chess.dummy.DummyPawn;
import java.util.List;

public class DummyPawnBuilder {

    private ChessBoard board;
    private ChessPosition position;
    private Color color;
    private List<ChessMovement> allAvailableMovements;

    private DummyPawnBuilder() {}

    public static DummyPawnBuilder builder() {
        return new DummyPawnBuilder();
    }

    public DummyPawnBuilder board(ChessBoard board) {
        this.board = board;
        return this;
    }

    public DummyPawnBuilder position(ChessPosition position) {
        this.position = position;
        return this;
    }

    public DummyPawnBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public DummyPawnBuilder allAvailableMovements(List<ChessMovement> allAvailableMovements) {
        this.allAvailableMovements = allAvailableMovements;
        return this;
    }

    public DummyPawn build() {
        return new DummyPawn(color, board, position, allAvailableMovements);
    }

}
