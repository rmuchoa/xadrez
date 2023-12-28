package board;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.format;

import board.mock.MockBoard;
import board.mock.MockBoard.StubMockBoard;
import board.mock.MockBoardPiece;
import board.mock.MockBoardPiece.StubMockBoardPiece;
import board.mock.MockBoardPosition;
import board.mock.MockBoardPosition.StubMockBoardPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardPieceTest {

    private StubMockBoard mockedBoard;
    private StubMockBoardPosition mockedPosition;
    private StubMockBoardPiece mockedPiece;

    @BeforeEach
    public void setUp() {
        mockedBoard = MockBoard.builder().filled().stub();
        mockedPosition = MockBoardPosition.builder().filled().stub();
        mockedPiece = MockBoardPiece.builder().position(mockedPosition).board(mockedBoard).stub();
    }

    @Test
    public void shouldPiecePositionMustBeEqualsThePositionThatPieceWasPlacedOn() {
        // given
        MockBoardPiece piece = MockBoardPiece.builder().board(mockedBoard).build();

        // when
        piece.placeOnPosition(mockedPosition);

        // then
        assertEquals(mockedPosition, piece.getPosition(), format("Positions are not equals! %s, %s", mockedPosition, piece.getPosition()));
    }

    @Test
    public void shouldPiecePositionMustBeRemoveFromFromPieceThatWasTakenOutOf() {
        // given
        MockBoardPiece piece = MockBoardPiece.builder().position(mockedPosition).board(mockedBoard).build();

        // when
        piece.takeOutOfPosition();

        // then
        assertNull(piece.getPosition(), format("Position are mistakenly present. %s", piece.getPosition()));
    }

    @Test
    public void shouldReturnPlacedPieceOnBoardWhenGetByPosition() {
        // given
        MockBoardPiece expectedPiece = MockBoardPiece.builder().position(mockedPosition).build();
        StubMockBoardPosition otherPosition = MockBoardPosition.builder().filled().stub();
        StubMockBoard board = MockBoard.builder().stubGetPiecePlacedOn(otherPosition, expectedPiece).filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().position(mockedPosition).board(board).build();

        // when
        MockBoardPiece realPiece = piece.getBoardPiecePlacedOn(otherPosition);

        // then
        board.verifyThatMethodGetPiecePlacedOnWasCalledAtLeastOnce(otherPosition);
        assertEquals(expectedPiece, realPiece, format("Positions are not equals! %s, %s", expectedPiece, realPiece));
    }

    @Test
    public void shouldReturnTrueWhenPositionDoesExistsOnBoard() {
        // given
        StubMockBoardPosition otherPosition = MockBoardPosition.builder().filled().stub();
        StubMockBoard board = MockBoard.builder().stubDoesExists(otherPosition, Boolean.TRUE).filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().position(mockedPosition).board(board).build();

        // when
        boolean existenceResult = piece.doesExistsOnBoard(otherPosition);

        // then
        board.verifyThatMethodDoesExistsWasCalledAtLeastOnce(otherPosition);
        assertTrue(existenceResult, format("Existence suppose to be true, but was %s!", existenceResult));
    }

    @Test
    public void shouldReturnFalseWhenPositionDoesNotExistsOnBoardOnAskingPieceDoesExistsOnBoard() {
        // given
        StubMockBoardPosition otherPosition = MockBoardPosition.builder().filled().stub();
        StubMockBoard board = MockBoard.builder().stubDoesExists(otherPosition, Boolean.FALSE).filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().position(mockedPosition).board(board).build();

        // when
        boolean existenceResult = piece.doesExistsOnBoard(otherPosition);

        // then
        board.verifyThatMethodDoesExistsWasCalledAtLeastOnce(otherPosition);
        assertFalse(existenceResult, format("Existence suppose to be false, but was %s!", existenceResult));
    }

    @Test
    public void shouldReturnTrueWhenPositionDoesNotExistsOnBoard() {
        // given
        StubMockBoardPosition otherPosition = MockBoardPosition.builder().filled().stub();
        StubMockBoard board = MockBoard.builder().stubDoesNotExists(otherPosition, Boolean.TRUE).filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().position(mockedPosition).board(board).build();

        // when
        boolean notExistenceResult = piece.doesNotExistsOnBoard(otherPosition);

        // then
        board.verifyThatMethodDoesNotExistsWasCalledAtLeastOnce(otherPosition);
        assertTrue(notExistenceResult, format("Not existence suppose to be true, but was %s!", notExistenceResult));
    }

    @Test
    public void shouldReturnFalseWhenPositionDoesExistsOnBoardOnAskingPieceDoesNotExistsOnBoard() {
        // given
        StubMockBoardPosition otherPosition = MockBoardPosition.builder().filled().stub();
        StubMockBoard board = MockBoard.builder().stubDoesNotExists(otherPosition, Boolean.FALSE).filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().position(mockedPosition).board(board).build();

        // when
        boolean notExistenceResult = piece.doesNotExistsOnBoard(otherPosition);

        // then
        board.verifyThatMethodDoesNotExistsWasCalledAtLeastOnce(otherPosition);
        assertFalse(notExistenceResult, format("Not existence suppose to be false, but was %s!", notExistenceResult));
    }

    @Test
    public void shouldReturnTrueOnCallingCanTargetThisWhenPositionIsOneOfAvailableTargetPositions() {
        // given
        StubMockBoardPosition targetPosition = MockBoardPosition.builder().filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().availableTargetPosition(targetPosition).position(mockedPosition).build();

        // when
        boolean targetable = piece.canTargetThis(targetPosition);

        // then
        assertTrue(targetable, format("Piece targetability suppose to be true, but was %s", targetable));
    }

    @Test
    public void shouldReturnFalseOnCallingCanTargetThisWhenPositionIsNotOneOfAvailableTargetPositions() {
        // given
        StubMockBoardPosition targetPosition = MockBoardPosition.builder().filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().availableTargetPosition(targetPosition).position(mockedPosition).build();

        // when
        StubMockBoardPosition otherPosition = MockBoardPosition.builder().filled().anotherMatrixColumn().stub();
        boolean targetable = piece.canTargetThis(otherPosition);

        // then
        assertFalse(targetable, format("Piece targetability suppose to be false, but was %s", targetable));
    }

    @Test
    public void shouldReturnTrueOnCallingCanNotTargetThisWhenPositionIsNotOneOfAvailableTargetPositions() {
        // given
        StubMockBoardPosition targetPosition = MockBoardPosition.builder().filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().availableTargetPosition(targetPosition).position(mockedPosition).build();

        // when
        StubMockBoardPosition otherPosition = MockBoardPosition.builder().filled().anotherMatrixColumn().stub();
        boolean targetable = piece.canNotTargetThis(otherPosition);

        // then
        assertTrue(targetable, format("Piece untargetability suppose to be true, but was %s", targetable));
    }

    @Test
    public void shouldReturnFalseOnCallingCanNotTargetThisWhenPositionIsOneOfAvailableTargetPositions() {
        // given
        StubMockBoardPosition targetPosition = MockBoardPosition.builder().filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().availableTargetPosition(targetPosition).position(mockedPosition).build();

        // when
        boolean targetable = piece.canNotTargetThis(targetPosition);

        // then
        assertFalse(targetable, format("Piece untargetability suppose to be false, but was %s", targetable));
    }

    @Test
    public void shouldReturnTrueOnCallingHasNoAvailableMovementsWhenAvailableTargetPositionsListIsEmpty() {
        // given
        MockBoardPiece piece = MockBoardPiece.builder().availableTargetPosition(null).position(mockedPosition).build();

        // when
        boolean imobility = piece.hasNoAvailableMovements();

        // then
        assertTrue(imobility, format("Piece imobility suppose to be true, but was %s", imobility));
    }

    @Test
    public void shouldReturnFalseOnCallingHasNoAvailableMovementsWhenAvailableTargetPositionsListHasAtLeastOncePosition() {
        // given
        StubMockBoardPosition targetPosition = MockBoardPosition.builder().filled().stub();
        MockBoardPiece piece = MockBoardPiece.builder().availableTargetPosition(targetPosition).position(mockedPosition).build();

        // when
        boolean imobility = piece.hasNoAvailableMovements();

        // then
        assertFalse(imobility, format("Piece imobility suppose to be false, but was %s", imobility));
    }

}
