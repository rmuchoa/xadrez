package chess;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import chess.dummy.DummyChessBoard;
import chess.dummy.DummyChessMovement;
import chess.dummy.DummyChessPiece;
import chess.pieces.King;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ChessPieceTest {

    private static final Integer ZERO = 0;
    private static final Integer ONE = 1;
    private static final Integer TWO = 2;

    @Mock
    ChessMatch match;
    @Mock
    ChessPosition position;
    @Mock
    ChessPosition otherPosition;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldAppliedMatchNeedsToBeEqualChessBoardMatchWhenCallingApplyMatch() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        piece.applyMatch();

        // then
        assertEquals(match, piece.getMatch(), format("Piece match suppose to be equals board match, but was'nt, was %s, and %s.", match, board.getMatch()));
    }

    @Test
    public void shouldAvailableMovementsNeedsToVanishedFromListWhenAskToPieceClearAvailableMovements() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        ChessMovement movement = mock(ChessMovement.class);
        piece.availableMovements.add(movement);

        // when
        piece.clearAvailableMovements();

        // then
        assertEquals(ZERO, piece.availableMovements.size());
    }

    @Test
    public void shouldAvailableMovementsNeedsToBePopulatedWithListedMovementsWhenAskToApplyAvailableMovements() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.availableMovements = new ArrayList<>();

        // when
        ChessMovement movement = mock(ChessMovement.class);
        piece.applyAvailableMovements(Collections.singletonList(movement));

        // then
        assertEquals(ONE, piece.availableMovements.size());
    }

    @Test
    public void shouldReturnTrueOnAskChessPieceIsWhitePieceWhenPieceColorIsWhite() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean isWhite = piece.isWhitePiece();

        // then
        assertTrue(isWhite, format("Chess piece isWhitePiece suppose to be true, but wasn't, was %s.", isWhite));
    }

    @Test
    public void shouldReturnFalseOnAskChessPieceIsWhitePieceWhenPieceColorIsBlack() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.BLACK).build();

        // when
        boolean isWhite = piece.isWhitePiece();

        // then
        assertFalse(isWhite, format("Chess piece isWhitePiece suppose to be false, but wasn't, was %s.", isWhite));
    }

    @Test
    public void shouldReturnTrueOnAskChessPieceIsBlackPieceWhenPieceColorIsBlack() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.BLACK).build();

        // when
        boolean isBlack = piece.isBlackPiece();

        // then
        assertTrue(isBlack, format("Chess piece isBlackPiece suppose to be true, but wasn't, was %s.", isBlack));
    }

    @Test
    public void shouldReturnFalseOnAskChessPieceIsBlackPieceWhenPieceColorIsWhite() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean isBlack = piece.isBlackPiece();

        // then
        assertFalse(isBlack, format("Chess piece isBlackPiece suppose to be false, but wasn't, was %s. Color[%s].", isBlack, piece.getColor()));
    }

    @Test
    public void shouldReturnTrueOnAskChessPieceIsKingWhenPieceIsAnInstanceOfKing() {
        // given
        King king = new King(Color.WHITE);

        // when
        boolean isKing = king.isKing();

        // then
        assertTrue(isKing, format("King piece isKing suppose to be true, but wasn't, was %s. Class[%s].", isKing, king.getClass()));
    }

    @Test
    public void shouldReturnFalseOnAskChessPieceIsKingWhenPieceIsNotAnInstanceOfKing() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean isKing = piece.isKing();

        // then
        assertFalse(isKing, format("Chess piece isKing suppose to be false, but wasn't, was %s. Class[%s].", isKing, piece.getClass()));
    }

    @Test
    public void shouldChessPieceNeedsToBeOpponentOfAnotherGivenPieceWhenTheColorIsDifferentOnBothPieces() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = DummyChessPiece.builder().board(board).position(position).color(Color.BLACK).build();

        // when
        boolean isOpponent = piece.isOpponentOf(otherPiece);

        // then
        assertTrue(isOpponent, format("Chess piece suppose to be opponent of other, but wasn't. piece [%s] other [%s].", piece, otherPiece));
    }

    @Test
    public void shouldChessPieceCannotBeOpponentOfAnotherGivenPieceWhenTheColorIsEqualsOnBothPieces() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean isOpponent = piece.isOpponentOf(otherPiece);

        // then
        assertFalse(isOpponent, format("Chess piece suppose not to be opponent from another, but was. piece [%s] another [%s].", piece, otherPiece));
    }

    @Test
    public void shouldChessPieceCannotBeOpponentOfAnotherGivenPieceWhenTheOtherPieceIsNull() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = null;

        // when
        boolean isOpponent = piece.isOpponentOf(otherPiece);

        // then
        assertFalse(isOpponent, format("Chess piece suppose not to be opponent from another, but was. piece [%s] another [%s].", piece, otherPiece));
    }

    @Test
    public void shouldChessPieceNeedsToBeCompanionOfAnotherGivenPieceWhenTheColorIsEqualsOnBothPieces() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean isCompanion = piece.isCompanionOf(otherPiece);

        // then
        assertTrue(isCompanion, format("Chess piece suppose to be companion of another, but wasn't. piece [%s] other [%s].", piece, otherPiece));
    }

    @Test
    public void shouldChessPieceCannotBeCompanionOfAnotherGivenPieceWhenTheColorIsDifferentOnBothPieces() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = DummyChessPiece.builder().board(board).position(position).color(Color.BLACK).build();

        // when
        boolean isCompanion = piece.isCompanionOf(otherPiece);

        // then
        assertFalse(isCompanion, format("Chess piece suppose not to be companion of other, but was. piece [%s] other [%s].", piece, otherPiece));
    }

    @Test
    public void shouldChessPieceCannotBeCompanionOfAnotherGivenPieceWhenTheOtherPieceIsNull() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = null;

        // when
        boolean isCompanion = piece.isCompanionOf(otherPiece);

        // then
        assertFalse(isCompanion, format("Chess piece suppose not to be opponent of other, but was. piece [%s] other [%s].", piece, otherPiece));
    }

    @Test
    public void shouldChessPieceNeedsToBeFromCurrentPlayerWhenPieceColorIsEqualsOfChessMatchCurrentPlayer() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        when(match.getCurrentPlayer()).thenReturn(Color.WHITE);

        // when
        boolean isCurrentPlayer = piece.isFromCurrentPlayer();

        // then
        assertTrue(isCurrentPlayer, format("Chess piece suppose to be from current player, but wasn't. color [%s] current player [%s].", piece.getColor(), match.getCurrentPlayer()));
    }

    @Test
    public void shouldChessPieceCannotBeFromCurrentPlayerWhenPieceColorIsDifferentOfChessMatchCurrentPlayer() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        when(match.getCurrentPlayer()).thenReturn(Color.BLACK);

        // when
        boolean isCurrentPlayer = piece.isFromCurrentPlayer();

        // then
        assertFalse(isCurrentPlayer, format("Chess piece cannot be from current player, but was. color [%s] current player [%s].", piece.getColor(), match.getCurrentPlayer()));
    }

    @Test
    public void shouldChessPieceNeedsToBeFromOpponentPlayerWhenPieceColorIsDifferentOfChessMatchCurrentPlayer() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        when(match.getCurrentPlayer()).thenReturn(Color.BLACK);

        // when
        boolean isOpponentPlayer = piece.isFromOpponentPlayer();

        // then
        assertTrue(isOpponentPlayer, format("Chess piece suppose to be from opponent player, but wasn't. color [%s] current player [%s].", piece.getColor(), match.getCurrentPlayer()));
    }

    @Test
    public void shouldChessPieceCannotBeFromOpponentPlayerWhenPieceColorIsEqualsOfChessMatchCurrentPlayer() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        when(match.getCurrentPlayer()).thenReturn(Color.WHITE);

        // when
        boolean isOpponentPlayer = piece.isFromOpponentPlayer();

        // then
        assertFalse(isOpponentPlayer, format("Chess piece cannot be from opponent player, but was. color [%s] current player [%s].", piece.getColor(), match.getCurrentPlayer()));
    }

    @Test
    public void shouldChessPieceNeedsToBeIncrementedWhenAskPieceToIncreaseMoveCount() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        piece.increaseMoveCount();

        // then
        assertEquals(ONE, piece.moveCount);
    }

    @Test
    public void shouldChessPieceNeedsToBeDecrementedWhenAskPieceToDecreaseMoveCount() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.moveCount = 1;

        // when
        piece.decreaseMoveCount();

        // then
        assertEquals(ZERO, piece.moveCount);
    }

    @Test
    public void shouldReturnTrueOnAskChessPieceThatHasAlreadyMovedWhenPieceMoveCountIsGreaterThanZero() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.moveCount = 1;

        // when
        boolean alreadyMoved = piece.hasAlreadyMoved();

        // then
        assertTrue(alreadyMoved, format("Chess piece suppose to has already moved, but wasn't. alreadyMoved[%s] moveCount[%s].", alreadyMoved, piece.moveCount));
    }

    @Test
    public void shouldReturnFalseOnAskChessPieceThatHasAlreadyMovedWhenPieceMoveCountIsLowerThanOne() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.moveCount = 0;

        // when
        boolean alreadyMoved = piece.hasAlreadyMoved();

        // then
        assertFalse(alreadyMoved, format("Chess piece cannot has already moved, but was. alreadyMoved[%s] moveCount[%s].", alreadyMoved, piece.moveCount));
    }

    @Test
    public void shouldReturnTrueOnAskChessPieceThatHasNotMovedYetWhenPieceMoveCountIsEqualZero() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.moveCount = 0;

        // when
        boolean notMovedYet = piece.hasNotMovedYet();

        // then
        assertTrue(notMovedYet, format("Chess piece suppose to has not moved yet, but was. notMovedYet[%s] moveCount[%s].", notMovedYet, piece.moveCount));
    }

    @Test
    public void shouldReturnFalseOnAskChessPieceThatHasNotMovedYetWhenPieceMoveCountIsGreaterThanZero() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.moveCount = 1;

        // when
        boolean notMovedYet = piece.hasNotMovedYet();

        // then
        assertFalse(notMovedYet, format("Chess piece suppose to has moved again, but wasn't. notMovedYet[%s] moveCount[%s].", notMovedYet, piece.moveCount));
    }

    @Test
    public void shouldChessPieceCannotBeEqualsFromAnObjectWhenItIsNotAnInstanceOfChessPiece() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean equalsResult = piece.equals(position);

        // then
        assertFalse(equalsResult, format("Chess piece suppose not to be equals, but was. equalsResult[%s].", equalsResult));
    }

    @Test
    public void shouldChessPieceCannotBeEqualsFromAnotherChessPieceWhenItHasADifferentColor() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = DummyChessPiece.builder().board(board).position(position).color(Color.BLACK).build();

        // when
        boolean equalsResult = piece.equals(otherPiece);

        // then
        assertFalse(equalsResult, format("Chess piece suppose not to be equals, but was. equalsResult[%s], piece.color[%s], otherPiece.color[%s].", equalsResult, piece.getColor(), otherPiece.getColor()));
    }

    @Test
    public void shouldChessPieceCannotBeEqualsFromAnotherChessPieceWhenItHasADifferentPosition() {
        // given
        ChessPosition differentPosition = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = DummyChessPiece.builder().board(board).position(differentPosition).color(Color.WHITE).build();

        // when
        boolean equalsResult = piece.equals(otherPiece);

        // then
        assertFalse(equalsResult, format("Chess piece suppose not to be equals, but was. equalsResult[%s], position[%s], differentPosition[%s].", equalsResult, position, differentPosition));
    }

    @Test
    public void shouldChessPieceNeedsToBeEqualsFromAnotherChessPieceWhenItHasADifferentColor() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        DummyChessPiece otherPiece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean equalsResult = piece.equals(otherPiece);

        // then
        assertTrue(equalsResult, format("Chess piece suppose to be equals, but wasn't. equalsResult[%s], piece.color[%s], otherPiece.color[%s], piece.position[%s], otherPiece.position[%s].", equalsResult, piece.getColor(), otherPiece.getColor(), piece.getPosition(), otherPiece.getPosition()));
    }

    @Test
    public void shouldClearAndApplyAllAvailableMovementsWhenCallingSetUpAvailableMovements() {
        // given
        ChessMovement movement2 = mock(ChessMovement.class);
        ChessMovement movement3 = mock(ChessMovement.class);
        List<ChessMovement> chessMovementList = Arrays.asList(movement2, movement3);

        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).allAvailableMovements(chessMovementList).build();
        ChessMovement movement1 = mock(ChessMovement.class);
        piece.availableMovements.add(movement1);

        // when
        piece.setUpAvailableMovements();

        // then
        assertEquals(TWO, piece.availableMovements.size());
        assertEquals(movement2, piece.getAvailableMovements().getFirst());
        assertEquals(movement3, piece.getAvailableMovements().getLast());
    }

    @Test
    public void shouldAskOtherPieceCanTargetThisPiecePositionWhenCallingCanBeTargetedByThisGivenOtherPiece() {
        // given
        ChessPiece otherPiece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        piece.canBeTargetedBy(otherPiece);

        // then
        verify(otherPiece).canTargetThis(position);
    }

    @Test
    public void shouldReturnFalseOnAskingIdPieceCanTargetPositionWhenPieceHasNoAvailableMovements() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.availableMovements = new ArrayList<>();

        // when
        boolean resultFlag = piece.canTargetThis(position);

        // then
        assertFalse(resultFlag, format("Piece suppose to cannot target position, but it can. position[%s], availableMovements[%s]", position, piece.availableMovements));
    }

    @Test
    public void shouldReturnFalseOnAskingIdPieceCanTargetPositionWhenPieceHasNoAvailableMovementsTargetingGivenPosition() {
        // given
        ChessPiece otherPiece = mock(ChessPiece.class);
        ChessMovement movement = DummyChessMovement.builder().piece(otherPiece).target(otherPosition).build();

        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.availableMovements = Arrays.asList(movement);

        // when
        boolean resultFlag = piece.canTargetThis(position);

        // then
        assertFalse(resultFlag, format("Piece suppose to cannot target position, but it can. position[%s], availableMovements[%s]", position, piece.availableMovements));
    }

    @Test
    public void shouldReturnTrueOnAskingIdPieceCanTargetPositionWhenPieceHasSomeAvailableMovementsTargetingGivenPosition() {
        // given
        ChessPiece otherPiece = mock(ChessPiece.class);
        ChessMovement movement = DummyChessMovement.builder().piece(otherPiece).target(position).build();

        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(otherPosition).color(Color.WHITE).build();
        piece.availableMovements = Arrays.asList(movement);

        // when
        boolean resultFlag = piece.canTargetThis(position);

        // then
        assertTrue(resultFlag, format("Piece suppose to can target position, but can't. position[%s], availableMovements[%s]", position, piece.availableMovements));
    }

    @Test
    public void shouldReturnTrueOnAskingIdPieceCanNotTargetPositionWhenPieceHasNoAvailableMovements() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.availableMovements = new ArrayList<>();

        // when
        boolean resultFlag = piece.canNotTargetThis(position);

        // then
        assertTrue(resultFlag, format("Piece suppose to cannot target position, but it can. position[%s], availableMovements[%s]", position, piece.availableMovements));
    }

    @Test
    public void shouldReturnTrueOnAskingIdPieceCanNotTargetPositionWhenPieceHasNoAvailableMovementsTargetingGivenPosition() {
        // given
        ChessPiece otherPiece = mock(ChessPiece.class);
        ChessMovement movement = DummyChessMovement.builder().piece(otherPiece).target(otherPosition).build();

        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.availableMovements = Arrays.asList(movement);

        // when
        boolean resultFlag = piece.canNotTargetThis(position);

        // then
        assertTrue(resultFlag, format("Piece suppose to cannot target position, but it can. position[%s], availableMovements[%s]", position, piece.availableMovements));
    }

    @Test
    public void shouldReturnFalseOnAskingIdPieceCanNotTargetPositionWhenPieceHasSomeAvailableMovementsTargetingGivenPosition() {
        // given
        ChessPiece otherPiece = mock(ChessPiece.class);
        ChessMovement movement = DummyChessMovement.builder().piece(otherPiece).target(position).build();

        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(otherPosition).color(Color.WHITE).build();
        piece.availableMovements = Arrays.asList(movement);

        // when
        boolean resultFlag = piece.canNotTargetThis(position);

        // then
        assertFalse(resultFlag, format("Piece suppose to can target position, but can't. position[%s], availableMovements[%s]", position, piece.availableMovements));
    }

    @Test
    public void shouldReturnTrueOnAskingPieceHasNoAvailableMovementsToWhenAvailableMovementsListIsEmpty() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.availableMovements = new ArrayList<>();

        // when
        boolean resultFlag = piece.hasNoAvailableMovements();

        // then
        assertTrue(resultFlag, format("Piece suppose to has no available movements, but has. availableMovements[%s]", piece.availableMovements));
    }

    @Test
    public void shouldReturnFalseOnAskingPieceHasNoAvailableMovementsToWhenAvailableMovementsListIsNotEmpty() {
        // given
        ChessPiece otherPiece = mock(ChessPiece.class);
        ChessMovement movement = DummyChessMovement.builder().piece(otherPiece).target(otherPosition).build();

        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();
        piece.availableMovements = Arrays.asList(movement);

        // when
        boolean resultFlag = piece.hasNoAvailableMovements();

        // then
        assertFalse(resultFlag, format("Piece suppose to has some available movements, but hasn't. availableMovements[%s]", piece.availableMovements));
    }

    @Test
    public void shouldNeverBeInCheckWhenChessPieceIsNotAKingPiece() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean resultFlag = piece.isInCheckPiece();

        // then
        assertFalse(resultFlag, format("Chess piece suppose not to be in check, but was. piece[%s]", piece));
    }

    @Test
    public void shouldNotBeInCheckWhenChessPieceIsAKingPieceButWithoutCheckFlag() {
        // given
        ChessPiece piece = new King(Color.WHITE);;

        // when
        boolean resultFlag = piece.isInCheckPiece();

        // then
        assertFalse(resultFlag, format("Chess piece suppose not to be in check, but was. piece[%s]", piece));
    }

    @Test
    public void shouldBeInCheckWhenChessPieceIsAKingPieceWithInformedCheck() {
        // given
        King king = new King(Color.WHITE);
        king.informCheck();

        // when
        boolean resultFlag = king.isInCheckPiece();

        // then
        assertTrue(resultFlag, format("Chess piece suppose to be in check, but wasn't. piece[%s]", king));
    }

    @Test
    public void shouldNeverBeInCheckMateWhenChessPieceIsNotAKingPiece() {
        // given
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(position).color(Color.WHITE).build();

        // when
        boolean resultFlag = piece.isInCheckMatePiece();

        // then
        assertFalse(resultFlag, format("Chess piece suppose not to be in check mate, but was. piece[%s]", piece));
    }

    @Test
    public void shouldNotBeInCheckMateWhenChessPieceIsAKingPieceButWithoutCheckMateFlag() {
        // given
        ChessPiece piece = new King(Color.WHITE);;

        // when
        boolean resultFlag = piece.isInCheckPiece();

        // then
        assertFalse(resultFlag, format("Chess piece suppose not to be in check, but was. piece[%s]", piece));
    }

    @Test
    public void shouldBeInCheckMateWhenChessPieceIsAKingPieceWithInformedCheckMate() {
        // given
        King king = new King(Color.WHITE);
        king.informCheck();

        // when
        boolean resultFlag = king.isInCheckPiece();

        // then
        assertTrue(resultFlag, format("Chess piece suppose to be in check mate, but wasn't. piece[%s]", king));
    }

}
