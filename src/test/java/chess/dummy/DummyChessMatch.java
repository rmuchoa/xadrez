package chess.dummy;

import chess.ChessBoard;
import chess.ChessMatch;
import chess.Color;

public class DummyChessMatch extends ChessMatch {

    public DummyChessMatch(ChessBoard board) {
        super(board, Color.WHITE, 0, false, false);
    }

    public DummyChessMatch(
        ChessBoard board,
        Color currentPlayer,
        int turn,
        boolean inCheck,
        boolean inCheckMate) {

        super(board, currentPlayer, turn, inCheck, inCheckMate);
    }

}
