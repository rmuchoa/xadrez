package chess.pieces;

import chess.ChessBoard;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import java.util.Collections;
import java.util.List;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Empty extends ChessPiece {

    private static final Color NO_COLOR = null;

    public Empty() {
        super(NO_COLOR);
    }

    public List<ChessPosition> getAllAvailableTargetPositions() {
        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return "-";
    }

    @Override
    public void applyAllAvailableMovements() {}

    @Override
    public ChessPiece clonePiece(ChessBoard clonedBoard) {
        return new Empty();
    }

}
