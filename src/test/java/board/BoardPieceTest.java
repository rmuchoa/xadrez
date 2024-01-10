package board;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.format;

import board.dummy.DummyBoard;
import board.dummy.DummyBoardPiece;
import board.dummy.DummyBoardPosition;
import board.dummy.builder.DummyBoardPositionBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class BoardPieceTest {

    @Mock
    private DummyBoard board;
    @Mock
    private DummyBoardPiece expectedPiece;
    @Mock
    private DummyBoardPosition expectedPosition;
    @Mock
    private DummyBoardPosition targetPosition;
    @Mock
    private DummyBoardPosition otherPosition;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldPiecePositionMustBeEqualsThePositionThatPieceWasPlacedOn() {
        // given
        DummyBoardPiece piece = DummyBoardPiece.builder().board(board).build();

        // when
        piece.placeOnPosition(expectedPosition, board);

        // then
        assertEquals(expectedPosition, piece.getPosition(), format("Positions are not equals! %s, %s", expectedPosition, piece.getPosition()));
        assertEquals(board, piece.getBoard(), format("Piece boards are not equals! %s, %s", board, piece.getBoard()));
    }

    @Test
    public void shouldPiecePositionMustBeRemoveFromFromPieceThatWasTakenOutOf() {
        // given
        DummyBoardPiece piece = DummyBoardPiece.builder().position(otherPosition).board(board).build();

        // when
        piece.takeOutOfPosition();

        // then
        assertNull(piece.getPosition(), format("Position are mistakenly present. %s", piece.getPosition()));
    }

    @Test
    public void shouldReturnPlacedPieceOnBoardWhenGetByPosition() {
        // given
        when(board.getPiecePlacedOn(eq(expectedPosition))).thenReturn(expectedPiece);
        DummyBoardPiece piece = DummyBoardPiece.builder().position(otherPosition).board(board).build();

        // when
        DummyBoardPiece resultantPiece = piece.getBoardPiecePlacedOn(expectedPosition);

        // then
        verify(board, atLeastOnce()).getPiecePlacedOn(eq(expectedPosition));
        assertEquals(expectedPiece, resultantPiece, format("Pieces are not equals! %s, %s", expectedPiece, resultantPiece));
    }

    @Test
    public void shouldReturnTrueWhenPositionDoesExistsOnBoard() {
        // given
        when(board.doesExists(eq(expectedPosition))).thenReturn(Boolean.TRUE);
        DummyBoardPiece piece = DummyBoardPiece.builder().position(otherPosition).board(board).build();

        // when
        boolean existenceResult = piece.doesExistsOnBoard(expectedPosition);

        // then
        verify(board, atLeastOnce()).doesExists(eq(expectedPosition));
        assertTrue(existenceResult, format("Existence suppose to be true, but was %s!", existenceResult));
    }

    @Test
    public void shouldReturnFalseWhenPositionDoesNotExistsOnBoardOnAskingPieceDoesExistsOnBoard() {
        // given
        when(board.doesExists(eq(expectedPosition))).thenReturn(Boolean.FALSE);
        DummyBoardPiece piece = DummyBoardPiece.builder().position(otherPosition).board(board).build();

        // when
        boolean existenceResult = piece.doesExistsOnBoard(expectedPosition);

        // then
        verify(board, atLeastOnce()).doesExists(eq(expectedPosition));
        assertFalse(existenceResult, format("Existence suppose to be false, but was %s!", existenceResult));
    }

    @Test
    public void shouldReturnTrueWhenPositionDoesNotExistsOnBoard() {
        // given
        when(board.doesNotExists(eq(expectedPosition))).thenReturn(Boolean.TRUE);
        DummyBoardPiece piece = DummyBoardPiece.builder().position(otherPosition).board(board).build();

        // when
        boolean notExistenceResult = piece.doesNotExistsOnBoard(expectedPosition);

        // then
        verify(board, atLeastOnce()).doesNotExists(eq(expectedPosition));
        assertTrue(notExistenceResult, format("Not existence suppose to be true, but was %s!", notExistenceResult));
    }

    @Test
    public void shouldReturnFalseWhenPositionDoesExistsOnBoardOnAskingPieceDoesNotExistsOnBoard() {
        // given
        when(board.doesNotExists(eq(expectedPosition))).thenReturn(Boolean.FALSE);
        DummyBoardPiece piece = DummyBoardPiece.builder().position(otherPosition).board(board).build();

        // when
        boolean notExistenceResult = piece.doesNotExistsOnBoard(expectedPosition);

        // then
        verify(board, atLeastOnce()).doesNotExists(eq(expectedPosition));
        assertFalse(notExistenceResult, format("Not existence suppose to be false, but was %s!", notExistenceResult));
    }

    @Test
    public void shouldReturnFalseOnCallingEqualsWhenComparedObjectIsNotAnInstanceOfBoardPiece() {
        // given
        BoardPosition comparingObject = mock(BoardPosition.class);
        DummyBoardPiece piece = DummyBoardPiece.builder().build();

        // when
        Boolean comparationResult = piece.equals(comparingObject);

        // then
        assertFalse(comparationResult, format("Compared object suppose to be an instance of another BoardPosition class, but was %s from class %s!", comparingObject, comparingObject.getClass()));
    }

    @Test
    public void shouldReturnFalseOnCallingEqualsWhenComparedBoardPieceIsPlacedOnADifferentBoardPosition() {
        // given
        BoardPiece comparingPiece = mock(BoardPiece.class);
        when(comparingPiece.getPosition()).thenReturn(otherPosition);
        DummyBoardPiece piece = DummyBoardPiece.builder().position(expectedPosition).build();

        // when
        boolean comparationResult = piece.equals(comparingPiece);

        // then
        assertFalse(comparationResult, format("Compared piece suppose to be placed on a different position, but was placed on the same %s!", expectedPosition));
    }

    @Test
    public void shouldReturnTrueOnCallingEqualsWhenComparedBoardPieceIsPlacedOnTheSameBoardPosition() {
        // given
        DummyBoardPiece comparingPiece = mock(DummyBoardPiece.class);
        when(comparingPiece.getPosition()).thenReturn(expectedPosition);
        DummyBoardPiece piece = DummyBoardPiece.builder().position(expectedPosition).build();

        // when
        boolean comparationResult = piece.equals(comparingPiece);

        // then
        assertTrue(comparationResult, format("Compared piece suppose to be placed on the same position %s, but was on a different position %s!", expectedPosition, otherPosition));
    }

    @Test
    public void shouldReturnExactlyBoardPositionToStringValueWhenCallBoardPieceToString() {
        // given
        String expectedString = "toString";
        DummyBoardPiece piece = DummyBoardPiece.builder().position(expectedPosition).build();
        when(expectedPosition.toString()).thenReturn(expectedString);

        // when
        String resultantString = piece.toString();

        // then
        assertEquals(expectedString, resultantString, format("Resultant toString() value suppose to be equals the expected value %s, but was different %s!", expectedString, resultantString));
    }

//    @Test
//    public void shouldReturnTrueOnCallingCanTargetThisWhenPositionIsOneOfAvailableTargetPositions() {
//        // given
//        DummyBoardPiece piece = DummyBoardPiece.builder().availableTargetPosition(targetPosition).position(expectedPosition).build();
//
//        // when
//        boolean targetable = piece.canTargetThis(targetPosition);
//
//        // then
//        assertTrue(targetable, format("Piece targetability suppose to be true, but was %s", targetable));
//    }
//
//    @Test
//    public void shouldReturnFalseOnCallingCanTargetThisWhenPositionIsNotOneOfAvailableTargetPositions() {
//        // given
//        when(otherPosition.getMatrixRow()).thenReturn(DummyBoardPositionBuilder.SECOND_MATRIX_ROW);
//        DummyBoardPiece piece = DummyBoardPiece.builder().availableTargetPosition(targetPosition).position(expectedPosition).build();
//
//        // when
//        boolean targetable = piece.canTargetThis(otherPosition);
//
//        // then
//        assertFalse(targetable, format("Piece targetability suppose to be false, but was %s", targetable));
//    }
//
//    @Test
//    public void shouldReturnTrueOnCallingCanNotTargetThisWhenPositionIsNotOneOfAvailableTargetPositions() {
//        // given
//        when(otherPosition.getMatrixColumn()).thenReturn(DummyBoardPositionBuilder.SECOND_MATRIX_COLUMN);
//        DummyBoardPiece piece = DummyBoardPiece.builder().availableTargetPosition(targetPosition).position(expectedPosition).build();
//
//        // when
//        boolean targetable = piece.canNotTargetThis(otherPosition);
//
//        // then
//        assertTrue(targetable, format("Piece untargetability suppose to be true, but was %s", targetable));
//    }
//
//    @Test
//    public void shouldReturnFalseOnCallingCanNotTargetThisWhenPositionIsOneOfAvailableTargetPositions() {
//        // given
//        DummyBoardPiece piece = DummyBoardPiece.builder().availableTargetPosition(targetPosition).position(expectedPosition).build();
//
//        // when
//        boolean targetable = piece.canNotTargetThis(targetPosition);
//
//        // then
//        assertFalse(targetable, format("Piece untargetability suppose to be false, but was %s", targetable));
//    }
//
//    @Test
//    public void shouldReturnTrueOnCallingHasNoAvailableMovementsWhenAvailableTargetPositionsListIsEmpty() {
//        // given
//        DummyBoardPiece piece = DummyBoardPiece.builder().availableTargetPosition(null).position(expectedPosition).build();
//
//        // when
//        boolean imobility = piece.hasNoAvailableMovements();
//
//        // then
//        assertTrue(imobility, format("Piece imobility suppose to be true, but was %s", imobility));
//    }
//
//    @Test
//    public void shouldReturnFalseOnCallingHasNoAvailableMovementsWhenAvailableTargetPositionsListHasAtLeastOncePosition() {
//        // given
//        DummyBoardPiece piece = DummyBoardPiece.builder().availableTargetPosition(targetPosition).position(expectedPosition).build();
//
//        // when
//        boolean imobility = piece.hasNoAvailableMovements();
//
//        // then
//        assertFalse(imobility, format("Piece imobility suppose to be false, but was %s", imobility));
//    }

}
