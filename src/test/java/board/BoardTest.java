package board;

import static board.dummy.builder.DummyBoardBuilder.TOTAL_COLUMNS;
import static board.dummy.builder.DummyBoardBuilder.TOTAL_ROWS;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.String.format;
import static org.mockito.MockitoAnnotations.openMocks;

import board.dummy.DummyBoard;
import board.dummy.DummyBoardPiece;
import board.dummy.DummyBoardPosition;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class BoardTest {

    @Mock
    private DummyBoardPiece otherPiece;
    @Mock
    private DummyBoardPiece expectedPiece;
    @Mock
    private DummyBoardPosition expectedPosition;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldThrowBoardExceptionOnBuildBoardWhenTotalRowsIsZero() {
        // given
        try {
            // when
            DummyBoard.dummyBuilder().totalRows(0).totalColumns().build();

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
            DummyBoard.dummyBuilder().totalRows().totalColumns(0).build();

            // then
            fail("The expected exception was not received");
        } catch (BoardException ex) {
            assertEquals("Error creating board with 8 rows and 0 columns: there must be at least 1 row and 1 column.", ex.getMessage(), format("The received exception message came different from expected."));
        }
    }

    @Test
    public void shouldReturnEmptyListOnCallingGetAllPlacedPiecesWhenBoardHasNoOnePlacedPiece() {
        // given
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(null, null).build();

        // when
        List<DummyBoardPiece> placedPieces = board.getAllPlacedPieces();

        // then
        assertTrue(placedPieces.isEmpty(), format("The placed pieces list suppose to be empty, but has %s items!", placedPieces.size()));
    }

    @Test
    public void shouldReturnAListContainingExpectedPieceOnCallingGetAllPlacedPiecesWhenBoardHasPlacedThatPiece() {
        // given
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(expectedPiece, expectedPosition).build();

        // when
        List<DummyBoardPiece> placedPieces = board.getAllPlacedPieces();
        DummyBoardPiece resultantPiece = placedPieces.get(0);

        // then
        assertFalse(placedPieces.isEmpty(), format("The placed pieces list suppose to not be empty, but has %s items!", placedPieces.size()));
        assertEquals(expectedPiece, resultantPiece, format("The resultant piece is different from expected! %s from %s and %s from %s.", resultantPiece, resultantPiece.getPosition(), expectedPiece, expectedPiece.getPosition()));
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingGetPiecePlacedOnWhenPassesBoardPositionWithNegativeMatrixRow() {
        // given
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow(-1).matrixColumn().build();

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
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow().matrixColumn(-1).build();

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
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow(TOTAL_ROWS+1).matrixColumn().build();

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
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow().matrixColumn(TOTAL_COLUMNS+1).build();

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
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow().matrixColumn().build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(expectedPiece, position).build();

        // when
        DummyBoardPiece resultantPiece = board.getPiecePlacedOn(position);

        // then
        assertEquals(expectedPiece, resultantPiece, format("Expected piece is not equals resultant piece. %s from %s and %s from %s.", expectedPiece, expectedPiece.getPosition(), resultantPiece, resultantPiece.getPosition()));
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingPlacePieceOnWhenAlreadyHaveSomePiecePlacedOnGivenPosition() {
        // given
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow().matrixColumn().build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(otherPiece, position).build();

        // then
        try {
            DummyBoardPiece newPiece = expectedPiece;
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
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(null, null).build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow().matrixColumn().build();

        // when
        board.placePieceOn(position, expectedPiece);

        // then
        List<List<DummyBoardPiece>> pieces = board.getAllPieces();
        DummyBoardPiece placedPiece = pieces.get(position.getMatrixRow()).get(position.getMatrixColumn());
        assertEquals(expectedPiece, placedPiece, format("The placed piece is not equals the given new piece informed. %s from %s and %s from %s.", placedPiece, placedPiece.getPosition(), expectedPiece, expectedPiece.getPosition()));
    }

    @Test
    public void shouldThrowBoardExceptionOnCallingRemovePieceFromWhenPassesBoardPositionWithNegativeMatrixRow() {
        // given
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow(-1).matrixColumn().build();

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
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow().matrixColumn(-1).build();

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
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow(TOTAL_ROWS+1).matrixColumn().build();

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
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();
        DummyBoardPosition position = DummyBoardPosition.builder().matrixRow().matrixColumn(TOTAL_COLUMNS+1).build();

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
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(null, givenPosition).build();

        // when
        DummyBoardPiece removedPiece = board.removePieceFrom(givenPosition);

        // then
        assertNull(removedPiece, format("Removed piece suppose to be null, but was not %s.", removedPiece));
    }

    @Test
    public void shouldReturnRemovedPieceOnCallingRemovePieceFromWhenPieceIsPlacedOnInformedBoardPosition() {
        // given
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(expectedPiece, expectedPosition).build();

        // when
        DummyBoardPiece removedPiece = board.removePieceFrom(expectedPosition);

        // then
        assertEquals(expectedPiece, removedPiece, format("Expected piece is not equals removed piece! %s from %s and %s from.", expectedPiece, expectedPiece.getPosition(), removedPiece, removedPiece.getPosition()));
    }

    @Test
    public void shouldTakeOutPieceFromPositionOnCallingRemovePieceFromWhenBoardRemovePieceFromPosition() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().withPlacedPiece(expectedPiece, expectedPosition).build();

        // when
        board.removePieceFrom(givenPosition);

        // then
        List<List<DummyBoardPiece>> pieces = board.getAllPieces();
        DummyBoardPiece piece = pieces.get(givenPosition.getMatrixRow()).get(givenPosition.getMatrixColumn());
        assertNull(piece, format("Matrix position supposed to be null on board, but was not %s.", piece));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasNegativeMatrixRow() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixRow(-1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasNegativeMatrixColumn() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixColumn(-1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasMatrixRowGreaterThanTotalRows() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixRow(TOTAL_ROWS+1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesExistsWhenPositionHasMatrixColumnGreaterThanTotalColumns() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixColumn(TOTAL_COLUMNS+1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionIsAValidPosition() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnFalseOnCallingDoesNotExistsWhenPositionIsAValidPosition() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertFalse(resultExistence, format("Result existence suppose to be false, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesNotExistsWhenPositionHasNegativeMatrixRow() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixRow(-1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionHasNegativeMatrixColumn() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixColumn(-1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionHasMatrixRowGreaterThanTotalRows() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixRow(TOTAL_ROWS+1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

    @Test
    public void shouldReturnTrueOnCallingDoesExistsWhenPositionHasMatrixColumnGreaterThanTotalColumns() {
        // given
        DummyBoardPosition givenPosition = DummyBoardPosition.builder().filled().matrixColumn(TOTAL_COLUMNS+1).build();
        DummyBoard board = DummyBoard.dummyBuilder().filled().build();

        // when
        boolean resultExistence = board.doesNotExists(givenPosition);

        // then
        assertTrue(resultExistence, format("Result existence suppose to be true, but was not! %s", resultExistence));
    }

}
