package chess;

import board.Board;
import chess.pieces.King;
import java.util.List;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class ChessBoard extends Board<ChessPosition, ChessPiece, ChessBoard> {

    public static final int CHESS_BOARD_SIZE = 8;

    public ChessBoard() {
        super(CHESS_BOARD_SIZE, CHESS_BOARD_SIZE);
    }

    public List<ChessPiece> getAllPlacedPiecesOf(Color color) {
        return getAllPlacedPieces().stream()
            .filter(piece -> piece.hasSameColorOf(color))
            .toList();
    }

    public King getOpponentKingFrom(Color currentPlayer) {
        Color oponentColor = Color.WHITE.equals(currentPlayer) ? Color.BLACK : Color.WHITE;
        return getKingOf(oponentColor);
    }

    public King getKingOf(Color color) {
        return getAllPlacedPiecesOf(color)
            .stream()
            .filter(ChessPiece::isKing)
            .map(piece -> (King) piece)
            .findAny()
            .orElse(null);
    }

}
