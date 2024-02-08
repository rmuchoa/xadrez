package chess.dummy;

import board.dummy.DummyBoard;
import board.dummy.DummyBoardPosition;
import board.dummy.builder.DummyBoardPieceBuilder;
import chess.ChessBoard;
import chess.ChessMatch;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import chess.dummy.builder.DummyChessPieceBuilder;
import java.util.List;

public class DummyChessPiece extends ChessPiece {

    private List<ChessMovement> allAvailableMovements;

    public DummyChessPiece(Color color, ChessBoard board, ChessPosition position, List<ChessMovement> allAvailableMovements) {
        super(color);
        this.allAvailableMovements = allAvailableMovements;
        placeOnPosition(position, board);
        applyMatch();
    }

    @Override
    public void applyAllAvailableMovements() {
        availableMovements.addAll(allAvailableMovements);
    }

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        return null;
    }

    public static DummyChessPieceBuilder builder() {
        return DummyChessPieceBuilder.builder();
    }

}
