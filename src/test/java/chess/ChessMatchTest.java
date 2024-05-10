package chess;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.assertTrue;

import chess.dummy.DummyChessMatch;
import chess.pieces.King;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

public class ChessMatchTest {

    @Mock
    private ChessBoard board;
    @Mock
    private ChessPiece piece;
    @Mock
    private ChessPosition position;
    @Captor
    private ArgumentCaptor<ChessPosition> positionCaptor;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldReturnFalseOnCallingIsInCheckWhenChessMatchInCheckIsFalse() {
        // given
        boolean inCheck = false;
        boolean inCheckMate = false;
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 0, inCheck, inCheckMate);

        // when
        boolean resultIsInCheck = match.isInCheck();

        // then
        assertFalse(resultIsInCheck, format("Result isInCheck suppose to be false, but wasn't. resultIsInCheck [%s]", resultIsInCheck));
    }

    @Test
    public void shouldReturnFalseOnIsInCheckWhenChessMatchInCheckIsTrueButInCheckMateIsTrueToo() {
        // given
        boolean inCheck = true;
        boolean inCheckMate = true;
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 0, inCheck, inCheckMate);

        // when
        boolean resultIsInCheck = match.isInCheck();

        // then
        assertFalse(resultIsInCheck, format("Result isInCheck suppose to be false, but wasn't. resultIsInCheck [%s]", resultIsInCheck));
    }

    @Test
    public void shouldReturnTrueIsInCheckWhenChessMatchInCheckIsTrueAndInCheckMateIsFalse() {
        // given
        boolean inCheck = true;
        boolean inCheckMate = false;
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 0, inCheck, inCheckMate);

        // when
        boolean resultIsInCheck = match.isInCheck();

        // then
        assertTrue(resultIsInCheck, format("Result isInCheck suppose to be true, but wasn't. resultIsInCheck [%s]", resultIsInCheck));
    }

    @Test
    public void shouldReturnTrueOnCallingIsNotInCheckMateWhenChessMatchInCheckMateIsFalse() {
        // given
        boolean inCheck = true;
        boolean inCheckMate = false;
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 0, inCheck, inCheckMate);

        // when
        boolean resultIsNotInCheckMate = match.isNotInCheckMate();

        // then
        assertTrue(resultIsNotInCheckMate, format("Result isNotInCheckMate suppose to be true, but wasn't. resultIsNotInCheckMate [%s]", resultIsNotInCheckMate));
    }

    @Test
    public void shouldReturnFalseOnIsNotInCheckMateWhenChessMatchInCheckMateIsTrue() {
        // given
        boolean inCheck = true;
        boolean inCheckMate = true;
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 0, inCheck, inCheckMate);

        // when
        boolean resultIsNotInCheckMate = match.isNotInCheckMate();

        // then
        assertFalse(resultIsNotInCheckMate, format("Result isNotInCheckMate suppose to be false, but wasn't. resultIsNotInCheckMate [%s]", resultIsNotInCheckMate));
    }

    @Test
    public void shouldAskBoardToPlaceGivenPieceOnSomePositionWhenCallingPlaceNewPiece() {
        // given
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.placeNewPiece('a', 1, piece);

        // then
        verify(board).placePieceOn(any(ChessPosition.class), eq(piece));
    }

    @Test
    public void shouldSendPositionContainingGivenColumnAndRowPositionValues() {
        // given
        char column = 'a';
        int row = 1;
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.placeNewPiece(column, row, piece);

        // then
        verify(board).placePieceOn(positionCaptor.capture(), eq(piece));
        assertEquals(column, positionCaptor.getValue().getChessColumn());
        assertEquals(row, positionCaptor.getValue().getChessRow());
    }

    @Test
    public void shouldAskBoardToGetAllPiecesWhenCallingGetPieces() {
        // given
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.getPieces();

        // then
        verify(board).getAllPieces();
    }

    @Test
    public void shouldReturnAllBoardPiecesObtained() {
        // given
        List<List<ChessPiece>> boardPieces = List.of(List.of(piece));
        when(board.getAllPieces()).thenReturn(boardPieces);
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        List<List<ChessPiece>> obtainedPieces = match.getPieces();

        // then
        assertEquals(boardPieces, obtainedPieces, format("Obtained pieces suppose to be exactly the same of board pieces, but wasn't. obtainedPieces [%s] boardPieces [%s]", obtainedPieces, boardPieces));
    }

    @Test
    public void shouldAskBoardToGetAllPlacedPiecesWhenSetUpAvailableMovements() {
        // given
        DummyChessMatch match = new DummyChessMatch(board);
        when(board.getAllPlacedPieces()).thenReturn(List.of(piece));

        // when
        match.setUpAvailableMovements();

        // then
        verify(board).getAllPlacedPieces();
    }

    @Test
    public void shouldAskPlacedPieceSetUpAvailableMovementsWhenCallingSetUpAvailableMovementsForMatch() {
        // given
        DummyChessMatch match = new DummyChessMatch(board);
        when(board.getAllPlacedPieces()).thenReturn(List.of(piece));

        // when
        match.setUpAvailableMovements();

        // then
        verify(piece).setUpAvailableMovements();
    }

    @Test
    public void shouldAskGetPlacedPieceOnPositionWhenCallingGetAvailableMovementsForThatPosition() {
        // given
        when(board.getPiecePlacedOn(position)).thenReturn(piece);
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.getAvailableMovementsFor(position);

        // then
        verify(board).getPiecePlacedOn(position);
    }

    @Test
    public void shouldAskBoardToValidateMobilityForPlacedPieceWhenCallingGetAvailableMovementsForPosition() {
        // given
        when(board.getPiecePlacedOn(position)).thenReturn(piece);
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.getAvailableMovementsFor(position);

        // then
        verify(board).validateMobilityFor(piece);
    }

    @Test
    public void shouldAskPlacedPieceGetAvailableMovementsWhenCallingGetAvailableMovementsForPosition() {
        // given
        when(board.getPiecePlacedOn(position)).thenReturn(piece);
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.getAvailableMovementsFor(position);

        // then
        verify(piece).getAvailableMovements();
    }

    @Test
    public void shouldAskBoardGetMovementForSourceAndTargetPositionWhenPerformingChessMoveByGivenPositions() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        ChessMovement movement = mock(ChessMovement.class);
        when(board.getMovementFor(source, target)).thenReturn(movement);
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.performChessMove(source, target);

        // then
        verify(board).getMovementFor(source, target);
    }

    @Test
    public void shouldAskDefinedMovementExecuteWhenPerformingChessMoveByGivenPositions() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        ChessMovement movement = mock(ChessMovement.class);
        when(board.getMovementFor(source, target)).thenReturn(movement);
        DummyChessMatch match = new DummyChessMatch(board);

        // when
        match.performChessMove(source, target);

        // then
        verify(movement).doMove();
    }

    @Test
    public void shouldIncrementTurnOnPerformingChessMoveWhenMatchIsNotInCheckMate() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        ChessMovement movement = mock(ChessMovement.class);
        when(board.getMovementFor(source, target)).thenReturn(movement);
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 0, false, false);

        // when
        match.performChessMove(source, target);

        // then
        assertFalse(match.isInCheckMate(), format("Match suppose not to be in check mate, but was. isInCheckMate [%s]", match.isInCheckMate()));
        assertEquals(1, match.getTurn(), format("Match turn suppose to be incremented, but wasn't. turn [%s]", match.getTurn()));
        assertEquals(Color.BLACK, match.getCurrentPlayer(), format("Match current player suppose to be BLACK, but wasn't. currentPlayer [%s]", match.getCurrentPlayer()));
    }

    @Test
    public void shouldNeverIncrementTurnOnPerformingChessMoveWhenMatchIsInCheckMate() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        ChessMovement movement = mock(ChessMovement.class);
        when(board.getMovementFor(source, target)).thenReturn(movement);
        when(board.isOpponentKingInCheckMate()).thenReturn(true);
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 1, true, true);

        // when
        match.performChessMove(source, target);

        // then
        assertTrue(match.isInCheckMate(), format("Match suppose to be in check mate, but wasn't. isInCheckMate [%s]", match.isInCheckMate()));
        assertEquals(1, match.getTurn(), format("Match turn suppose not to be incremented, but was. turn [%s]", match.getTurn()));
        assertEquals(Color.WHITE, match.getCurrentPlayer(), format("Match current player suppose to stay WHITE, but didn't. currentPlayer [%s]", match.getCurrentPlayer()));
    }

    @Test
    public void shouldReturnCapturedPieceFromMovementExecutionWhenPerformChessMove() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        ChessMovement movement = mock(ChessMovement.class);
        when(board.getMovementFor(source, target)).thenReturn(movement);
        when(movement.doMove()).thenReturn(piece);
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 1, false, false);

        // when
        ChessPiece capturedPiece = match.performChessMove(source, target);

        // then
        assertEquals(piece, capturedPiece, format("Captured piece suppose to be equals from movement execution, but wasn't. capturedPiece[%s] capturedPieceExecution[%s]", capturedPiece, piece));
    }

    @Test
    public void shouldUndoMovementWhenCatchChessExceptionFromCheckOwnKingMovement() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        ChessMovement movement = mock(ChessMovement.class);
        ChessPiece capturedPiece = mock(ChessPiece.class);
        DummyChessMatch match = new DummyChessMatch(board, Color.WHITE, 1, false, false);
        when(board.getMovementFor(source, target)).thenReturn(movement);
        when(movement.doMove()).thenReturn(capturedPiece);
        doThrow(ChessException.class).when(board).checkOwnKing();

        // when
        assertThrows(ChessException.class, () -> {
            match.performChessMove(source, target);
        });

        // then
        verify(movement).undoMove(capturedPiece);
    }

    @Test
    public void shouldApplyMatchPropertiesOnNewerMatchWhenCloneChessMatch() {
        // given
        int turn = 1;
        boolean inCheck = false;
        boolean inCheckMate = false;
        Color currentPlayer = Color.WHITE;
        DummyChessMatch match = new DummyChessMatch(board, currentPlayer, turn, inCheck, inCheckMate);

        // when
        ChessMatch clonedMatch = match.cloneMatch();

        // then
        assertEquals(turn, clonedMatch.getTurn(), format("Cloned turn suppose to be equals inputted turn, but wasn't. turn [%s] clonedTurn [%s]", turn, clonedMatch.getTurn()));
        assertEquals(inCheck, clonedMatch.isInCheck(), format("Cloned inCheck suppose to be equals inputted inCheck, but wasn't. inCheck [%s] clonedInCheck [%s]", inCheck, clonedMatch.isInCheck()));
        assertEquals(inCheckMate, clonedMatch.isInCheckMate(), format("Cloned inCheckMate suppose to be equals inputted inCheckMate, but wasn't. inCheckMate [%s] clonedInCheckMate [%s]", inCheckMate, clonedMatch.isInCheckMate()));
        assertEquals(currentPlayer, clonedMatch.getCurrentPlayer(), format("Cloned currentPlayer suppose to be equals inputted currentPlayer, but wasn't. currentPlayer [%s] clonedCurrentPlayer [%s]", currentPlayer, clonedMatch.getCurrentPlayer()));
        verify(board).cloneIntoBoard(eq(clonedMatch.getBoard()));
    }

    @Test
    public void shouldAskBoardIfCanDetectCheckForKingWhenTestingMatchCanDetectCheckForGivenKing() {
        // given
        int turn = 1;
        boolean inCheck = false;
        boolean inCheckMate = false;
        Color currentPlayer = Color.WHITE;
        DummyChessMatch match = new DummyChessMatch(board, currentPlayer, turn, inCheck, inCheckMate);
        King king = mock(King.class);

        // when
        match.canDetectCheckFor(king);

        // then
        verify(board).canDetectCheckFor(eq(king));
    }

}
