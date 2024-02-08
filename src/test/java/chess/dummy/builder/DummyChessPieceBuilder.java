package chess.dummy.builder;

import chess.ChessMovement;
import chess.ChessPosition;
import chess.Color;
import chess.dummy.DummyChessBoard;
import chess.dummy.DummyChessPiece;
import java.util.List;

public class DummyChessPieceBuilder {

    private DummyChessBoard board;
    private ChessPosition position;
    private Color color;
    private List<ChessMovement> allAvailableMovements;

    private DummyChessPieceBuilder() {}

    public static DummyChessPieceBuilder builder() {
        return new DummyChessPieceBuilder();
    }

    public DummyChessPieceBuilder board(DummyChessBoard board) {
        this.board = board;
        return this;
    }

    public DummyChessPieceBuilder position(ChessPosition position) {
        this.position = position;
        return this;
    }

    public DummyChessPieceBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public DummyChessPieceBuilder allAvailableMovements(List<ChessMovement> allAvailableMovements) {
        this.allAvailableMovements = allAvailableMovements;
        return this;
    }

    public DummyChessPiece build() {
        return new DummyChessPiece(color, board, position, allAvailableMovements);
    }
}
