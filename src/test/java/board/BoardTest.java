package board;

import static board.mock.MockBoard.TOTAL_COLUMNS;
import static board.mock.MockBoard.TOTAL_ROWS;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.format;

import board.mock.MockBoard;
import board.mock.MockBoardPiece;
import board.mock.MockBoardPiece.StubMockBoardPiece;
import board.mock.MockBoardPosition;
import board.mock.MockBoardPosition.StubMockBoardPosition;
import java.util.List;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void shouldThrowBoardExceptionOnBuildBoardWhenTotalRowsIsZero() {
        // given
        try {
            // when
            MockBoard.builder().totalRows(0).totalColumns().build();

            // then
            fail("The expected exception was not received");
        } catch (BoardException ex) {
            assertEquals("Error creating board with 0 rows and 8 columns: there must be at least 1 row and 1 column.", ex.getMessage(), format("The received exception message came different from expected."));
        }
    }

    @Test
    public void shouldThrowBoardExceptionOnBuildBoardWhenTotalColumnsIsZero() {
        // given
        try {
            // when
            MockBoard.builder().totalRows().totalColumns(0).build();

            // then
            fail("The expected exception was not received");
        } catch (BoardException ex) {
            assertEquals("Error creating board with 8 rows and 0 columns: there must be at least 1 row and 1 column.", ex.getMessage(), format("The received exception message came different from expected."));
        }
    }

    @Test
    public void shouldReturnEmptyListOnCallingGetAllPlacedPiecesWhenBoardHasNoOnePlacedPiece() {
        // given
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(null, null).build();

        // when
        List<MockBoardPiece> placedPieces = board.getAllPlacedPieces();

        // then
        assertTrue(placedPieces.isEmpty(), format("The placed pieces list suppose to be empty, but has %s items!", placedPieces.size()));
    }

    @Test
    public void shouldReturnAListContainingExpectedPieceOnCallingGetAllPlacedPiecesWhenBoardHasPlacedThatPiece() {
        // given
        StubMockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn().stub();
        StubMockBoardPiece expectedPiece = MockBoardPiece.builder().position(position).stub();
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(expectedPiece, position).build();

        // when
        List<MockBoardPiece> placedPieces = board.getAllPlacedPieces();
        MockBoardPiece resultantPiece = placedPieces.get(0);

        // then
        assertFalse(placedPieces.isEmpty(), format("The placed pieces list suppose to not be empty, but has %s items!", placedPieces.size()));
        assertEquals(expectedPiece, resultantPiece, format("The resultant piece is different from expected! %s from %s and %s from %s.", resultantPiece, resultantPiece.getPosition(), expectedPiece, expectedPiece.getPosition()));
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingGetPiecePlacedOnWhenPassesBoardPositionWithNegativeMatrixRow() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow(-1).matrixColumn().build();

        // when
        try {
            board.getPiecePlacedOn(position);

        // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[-1], column[0]", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingGetPiecePlacedOnWhenPassesBoardPositionWithNegativeMatrixColumn() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn(-1).build();

        // when
        try {
            board.getPiecePlacedOn(position);

            // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[0], column[-1]", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingGetPiecePlacedOnWhenPassesBoardPositionWithMatrixRowGreaterThanTotalRows() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow(TOTAL_ROWS+1).matrixColumn().build();

        // when
        try {
            board.getPiecePlacedOn(position);

            // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[9], column[0]", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingGetPiecePlacedOnWhenPassesBoardPositionWithMatrixColumnGreaterThanTotalColumns() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn(TOTAL_COLUMNS+1).build();

        // when
        try {
            board.getPiecePlacedOn(position);

            fail("The expected exception was not properly thrown");
            // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[0], column[9]", ex.getMessage());
        }
    }

    @Test
    public void shouldReturnGivenPieceOnCallingGetPiecePlacedOnWhenAskingForPlacedPosition() {
        // given
        MockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn().build();
        StubMockBoardPiece expectedPiece = MockBoardPiece.builder().position(position).stub();
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(expectedPiece, position).build();

        // when
        MockBoardPiece resultantPiece = board.getPiecePlacedOn(position);

        // then
        assertEquals(expectedPiece, resultantPiece, format("Expected piece is not equals resultant piece. %s from %s and %s from %s.", expectedPiece, expectedPiece.getPosition(), resultantPiece, resultantPiece.getPosition()));
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingPlacePieceOnWhenAlreadyHaveSomePiecePlacedOnGivenPosition() {
        // given
        MockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn().build();
        StubMockBoardPiece otherPiece = MockBoardPiece.builder().position(position).stub();
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(otherPiece, position).build();

        // then
        try {
            StubMockBoardPiece newPiece = MockBoardPiece.builder().position(position).stub();
            board.placePieceOn(position, newPiece);

            fail("The expected exception was not properly thrown");

        // given
        } catch (BoardException ex) {
            assertEquals(format("There is already a piece on position %s", position), ex.getMessage());
        }
    }

    @Test
    public void shouldPlacePieceOnBoardPositionOnCallingPlacePieceOnWhenGivenPositionIsAvailableForNewPlacements() {
        // given
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(null, null).build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn().build();
        StubMockBoardPiece newPiece = MockBoardPiece.builder().position(position).stub();

        // when
        board.placePieceOn(position, newPiece);

        // then
        List<List<MockBoardPiece>> pieces = board.getAllPieces();
        MockBoardPiece placedPiece = pieces.get(position.getMatrixRow()).get(position.getMatrixColumn());
        assertEquals(newPiece, placedPiece, format("The placed piece is not equals the given new piece informed. %s from %s and %s from %s.", placedPiece, placedPiece.getPosition(), newPiece, newPiece.getPosition()));
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingRemovePieceFromWhenPassesBoardPositionWithNegativeMatrixRow() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow(-1).matrixColumn().build();

        // when
        try {
            board.removePieceFrom(position);

        // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[-1], column[0]", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingRemovePieceFromWhenPassesBoardPositionWithNegativeMatrixColumn() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn(-1).build();

        // when
        try {
            board.removePieceFrom(position);

            // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[0], column[-1]", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingRemovePieceFromWhenPassesBoardPositionWithMatrixRowGreaterThanTotalRows() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow(TOTAL_ROWS+1).matrixColumn().build();

        // when
        try {
            board.removePieceFrom(position);

            // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[9], column[0]", ex.getMessage());
        }
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingRemovePieceFromWhenPassesBoardPositionWithMatrixColumnGreaterThanTotalColumns() {
        // given
        MockBoard board = MockBoard.builder().filled().build();
        MockBoardPosition position = MockBoardPosition.builder().matrixRow().matrixColumn(TOTAL_COLUMNS+1).build();

        // when
        try {
            board.removePieceFrom(position);

            // then
        } catch (BoardException ex) {
            assertEquals("Position not on the board. row[0], column[9]", ex.getMessage());
        }
    }

    @Test
    public void shouldReturnNullPieceOnCallingRemovePieceFromWhenBoardPositionIsEmpty() {
        // given
        MockBoardPosition givenPosition = MockBoardPosition.builder().filled().build();
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(null, givenPosition).build();

        // when
        MockBoardPiece removedPiece = board.removePieceFrom(givenPosition);

        // then
        assertNull(removedPiece, format("Removed piece suppose to be null, but was not %s.", removedPiece));
    }

    @Test
    public void shouldReturnRemovedPieceOnCallingRemovePieceFromWhenPieceIsPlacedOnInformedBoardPosition() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().stub();
        StubMockBoardPiece expectedPiece = MockBoardPiece.builder().position(givenPosition).stub();
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(expectedPiece, givenPosition).build();

        // when
        MockBoardPiece removedPiece = board.removePieceFrom(givenPosition);

        // then
        assertEquals(expectedPiece, removedPiece, format("Expected piece is not equals removed piece! %s from %s and %s from.", expectedPiece, expectedPiece.getPosition(), removedPiece, removedPiece.getPosition()));
    }

    @Test
    public void shouldTakeOutPieceFromPositionOnCallingRemovePieceFromWhenBoardRemovePieceFromPosition() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().stub();
        StubMockBoardPiece expectedPiece = MockBoardPiece.builder().position(givenPosition).stub();
        MockBoard board = MockBoard.builder().filled().withPlacedPiece(expectedPiece, givenPosition).build();

        // when
        board.removePieceFrom(givenPosition);

        // then
        List<List<MockBoardPiece>> pieces = board.getAllPieces();
        MockBoardPiece piece = pieces.get(givenPosition.getMatrixRow()).get(givenPosition.getMatrixColumn());
        assertNull(piece, format("Matrix position supposed to be null on board, but was not %s.", piece));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasNegativeMatrixRow() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixRow(-1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasNegativeMatrixColumn() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixColumn(-1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasMatrixRowGreaterThanTotalRows() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixRow(TOTAL_ROWS+1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasMatrixColumnGreaterThanTotalColumns() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixColumn(TOTAL_COLUMNS+1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionIsAValidPosition() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesNotExistsWhenPositionIsAValidPosition() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesNotExistsWhenPositionHasNegativeMatrixRow() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixRow(-1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionHasNegativeMatrixColumn() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixColumn(-1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionHasMatrixRowGreaterThanTotalRows() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixRow(TOTAL_ROWS+1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionHasMatrixColumnGreaterThanTotalColumns() {
        // given
        StubMockBoardPosition givenPosition = MockBoardPosition.builder().filled().matrixColumn(TOTAL_COLUMNS+1).stub();
        MockBoard board = MockBoard.builder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

}
