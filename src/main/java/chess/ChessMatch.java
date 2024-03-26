package chess;

import chess.pieces.King;
import java.util.List;
import lombok.Getter;

public class ChessMatch {

    @Getter
    private int turn;
    private boolean inCheck;
    @Getter
    private boolean inCheckMate;
    @Getter
    private Color currentPlayer;
    @Getter
    private final ChessBoard board;

    public ChessMatch() {
        this.board = new ChessBoard(this);
        this.currentPlayer = Color.WHITE;
        this.turn = 1;
    }

    protected ChessMatch(ChessBoard board, Color currentPlayer, int turn, boolean inCheck, boolean inCheckMate) {
        this.board = board;
        this.currentPlayer = currentPlayer;
        this.turn = turn;
        this.inCheck = inCheck;
        this.inCheckMate = inCheckMate;
    }

    public boolean isInCheck() {
        return inCheck && !inCheckMate;
    }

    public boolean isNotInCheckMate() {
        return !isInCheckMate();
    }

    public void placeNewPiece(char column, int row, ChessPiece piece) {
        ChessPosition position = new ChessPosition(column, row);

        board.placePieceOn(position, piece);
    }

    public List<List<ChessPiece>> getPieces() {
        return board.getAllPieces();
    }

    public void setUpAvailableMovements() {
        board.getAllPlacedPieces()
            .forEach(ChessPiece::setUpAvailableMovements);
    }

    public List<ChessMovement> getAvailableMovementsFor(ChessPosition sourcePosition) {
        ChessPiece piece = board.getPiecePlacedOn(sourcePosition);
        board.validateMobilityFor(piece);
        
        return piece.getAvailableMovements();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        ChessMovement movement = board.getMovementFor(sourcePosition, targetPosition);

        ChessPiece capturedPiece = movement.doMove();
        checkMatch(movement, capturedPiece);

        if (!inCheckMate)
            nextTurn();

        return capturedPiece;
    }

    private void checkMatch(ChessMovement movement, ChessPiece capturedPiece) {
        try {
            board.checkOwnKing();
        } catch (ChessException ex) {
            movement.undoMove(capturedPiece);
            throw ex;
        }

        inCheck = board.isOpponentKingInCheck();
        inCheckMate = board.isOpponentKingInCheckMate();
    }

    private void nextTurn() {
        turn ++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    public ChessMatch cloneMatch() {
        ChessMatch clonedMatch = new ChessMatch();
        clonedMatch.turn = getTurn();
        clonedMatch.inCheck = isInCheck();
        clonedMatch.inCheckMate = isInCheckMate();
        clonedMatch.currentPlayer = getCurrentPlayer();
        getBoard().cloneIntoBoard(clonedMatch.getBoard());
        return clonedMatch;
    }

    public boolean canDetectCheckFor(King king) {
        return board.canDetectCheckFor(king);
    }

}
