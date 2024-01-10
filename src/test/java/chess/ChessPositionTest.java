package chess;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import board.BoardPiece;
import board.dummy.DummyBoardPiece;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class ChessPositionTest {

    @InjectMocks
    private ChessPosition position;

    @Test
    public void shouldConvertMatrixBoardPositionRowAndColumnToChessPositionRowAndColumn() {
        // given
        int matrixRow = 1;
        int matrixColumn = 1;
        int expectedChessRow = 7;
        char expectedChessColumn = 'b';

        // when
        ChessPosition piece = new ChessPosition(matrixRow, matrixColumn);

        // then
        assertEquals(expectedChessRow, piece.getChessRow(), format("Piece chessRow suppose to be %s, but was %s!", expectedChessRow, piece.getChessRow()));
        assertEquals(expectedChessColumn, piece.getChessColumn(), format("Piece chessColumn suppose to be %s, but was %s!", expectedChessColumn, piece.getChessColumn()));
    }

    @Test
    public void shouldConvertChessPositionRowAndColumnToSuperClassBoardPositionMatrixRowAndColumn() {
        // given
        char chessColumn = 'c';
        int chessRow = 2;
        int expectedMatrixRow = 6;
        int expectedMatrixColumn = 2;

        // when
        ChessPosition piece = new ChessPosition(chessColumn, chessRow);

        // then
        assertEquals(expectedMatrixRow, piece.getMatrixRow(), format("Piece matrixRow suppose to be %s, but was %s!", expectedMatrixRow, piece.getMatrixRow()));
        assertEquals(expectedMatrixColumn, piece.getMatrixColumn(), format("Piece matrixColumn suppose to be %s, but was %s!", expectedMatrixColumn, piece.getMatrixColumn()));
    }

    @Test
    public void shouldThrowChessExceptionWhenTryingToConstructChessPositionWithChessColumnLowerThanCharacterA() {
        // given
        char chessColumn = 'a'-3;
        int chessRow = 2;
        String expectedMessage = "Error instantiating ChessPosition "+chessColumn+chessRow+". Valid values are from a1 to h8.";

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            ChessPosition.buildPositionFor(chessColumn, chessRow);
        });

        // then
        assertEquals(expectedMessage, thrown.getMessage(), format("ChessException message suppose to be equals '%s', but was different %s!", expectedMessage, thrown.getMessage()));
    }

    @Test
    public void shouldThrowChessExceptionWhenTryingToConstructChessPositionWithChessColumnGreaterThanCharacterH() {
        // given
        char chessColumn = 'j';
        int chessRow = 2;
        String expectedMessage = "Error instantiating ChessPosition "+chessColumn+chessRow+". Valid values are from a1 to h8.";

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            ChessPosition.buildPositionFor(chessColumn, chessRow);
        });

        // then
        assertEquals(expectedMessage, thrown.getMessage(), format("ChessException message suppose to be equals '%s', but was different %s!", expectedMessage, thrown.getMessage()));
    }

    @Test
    public void shouldThrowChessExceptionWhenTryingToConstructChessPositionWithChessRowLowerThanValue1() {
        // given
        char chessColumn = 'b';
        int chessRow = -1;
        String expectedMessage = "Error instantiating ChessPosition "+chessColumn+chessRow+". Valid values are from a1 to h8.";

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            ChessPosition.buildPositionFor(chessColumn, chessRow);
        });

        // then
        assertEquals(expectedMessage, thrown.getMessage(), format("ChessException message suppose to be equals '%s', but was different %s!", expectedMessage, thrown.getMessage()));
    }

    @Test
    public void shouldThrowChessExceptionWhenTryingToConstructChessPositionWithChessRowGreaterThanValue8() {
        // given
        char chessColumn = 'c';
        int chessRow = 11;
        String expectedMessage = "Error instantiating ChessPosition "+chessColumn+chessRow+". Valid values are from a1 to h8.";

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            ChessPosition.buildPositionFor(chessColumn, chessRow);
        });

        // then
        assertEquals(expectedMessage, thrown.getMessage(), format("ChessException message suppose to be equals '%s', but was different %s!", expectedMessage, thrown.getMessage()));
    }

    @Test
    public void shouldReturnSameChessColumnWhenCallChessPositionGetSameChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getSameChessColumn();

        // then
        assertEquals(chessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", chessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnChessColumnPlusPlusWhenCallChessPositionGetNextEastChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        char expectedChessColumn = 'b';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getNextEastChessColumn();

        // then
        assertEquals(expectedChessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnChessColumnMinusMinusWhenCallChessPositionGetNextWestChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'd';
        char expectedChessColumn = 'c';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getNextWestChessColumn();

        // then
        assertEquals(expectedChessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnChessColumnPlusTwoWhenCallChessPositionGetTwoBesideEastChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        char expectedChessColumn = 'c';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getTwoBesideEastChessColumn();

        // then
        assertEquals(expectedChessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnChessColumnMinusTwoWhenCallChessPositionGetTwoBesideWestChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'd';
        char expectedChessColumn = 'b';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getTwoBesideWestChessColumn();

        // then
        assertEquals(expectedChessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnChessColumnPlusThreeWhenCallChessPositionGetThreeBesideEastChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        char expectedChessColumn = 'd';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getThreeBesideEastChessColumn();

        // then
        assertEquals(expectedChessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnChessColumnMinusThreeWhenCallChessPositionGetThreeBesideWestChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'd';
        char expectedChessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getThreeBesideWestChessColumn();

        // then
        assertEquals(expectedChessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnChessColumnMinusFourWhenCallChessPositionGetFourBesideWestChessColumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'e';
        char expectedChessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        char resultantChessColumn = position.getFourBesideWestChessColumn();

        // then
        assertEquals(expectedChessColumn, resultantChessColumn, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessColumn, resultantChessColumn));
    }

    @Test
    public void shouldReturnSameChessRowWhenCallChessPositionGetSameChessRow() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        int resultantChessRow = position.getSameChessRow();

        // then
        assertEquals(chessRow, resultantChessRow, format("Chess column suppose to be equals '%s', but was different '%s'!", chessRow, resultantChessRow));
    }

    @Test
    public void shouldReturnChessRowPlus1WhenCallChessPositionGetNextNorthChessRow() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        int expectedChessRow = 2;
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        int resultantChessRow = position.getNextNorthChessRow();

        // then
        assertEquals(expectedChessRow, resultantChessRow, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessRow, resultantChessRow));
    }

    @Test
    public void shouldReturnChessRowMinus1WhenCallChessPositionGetNextSouthChessRow() {
        // given
        int chessRow = 4;
        char chessColumn = 'a';
        int expectedChessRow = 3;
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        int resultantChessRow = position.getNextSouthChessRow();

        // then
        assertEquals(expectedChessRow, resultantChessRow, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessRow, resultantChessRow));
    }

    @Test
    public void shouldReturnChessRowPlusTwoWhenCallChessPositionGetTwoAheadNorthChessRow() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        int expectedChessRow = 3;
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        int resultantChessRow = position.getTwoAheadNorthChessRow();

        // then
        assertEquals(expectedChessRow, resultantChessRow, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessRow, resultantChessRow));
    }

    @Test
    public void shouldReturnChessRowMinusTwoWhenCallChessPositionGetTwoBehindSouthChessRow() {
        // given
        int chessRow = 4;
        char chessColumn = 'a';
        int expectedChessRow = 2;
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        int resultantChessRow = position.getTwoBehindSouthChessRow();

        // then
        assertEquals(expectedChessRow, resultantChessRow, format("Chess column suppose to be equals '%s', but was different '%s'!", expectedChessRow, resultantChessRow));
    }

    @Test
    public void shouldReturnStringConcatenatingChessColumnAndChessRowWhenCallChessPositionToString() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        String expectedString = "a1";
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        String resultantString = position.toString();

        // then
        assertEquals(expectedString, resultantString, format("BoardPosition toString() suppose to be '%s', but was different '%s'!", expectedString, resultantString));
    }

    @Test
    public void shouldReturnFalseOnCallingEqualsWhenComparedObjectIsNotAnInstanceOfChessPosition() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);
        ChessPiece comparingObject = mock(ChessPiece.class);

        // when
        Boolean comparationResult = position.equals(comparingObject);

        // then
        assertFalse(comparationResult, format("Compared object suppose to be an instance of another ChessPiece class, but was %s from class %s!", comparingObject, comparingObject.getClass()));
    }

    @Test
    public void shouldReturnFalseOnCallingEqualsWhenComparedChessPositionIsPlacedOnADifferentChessCollumn() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        char otherChessColumn = 'c';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);
        ChessPosition comparingPosition = ChessPosition.buildPositionFor(otherChessColumn, chessRow);

        // when
        boolean comparationResult = position.equals(comparingPosition);

        // then
        assertFalse(comparationResult, format("Compared piece suppose to be placed on a different chessColumn '%s', but was placed on the same!", chessColumn));
    }

    @Test
    public void shouldReturnFalseOnCallingEqualsWhenComparedChessPositionIsPlacedOnADifferentChessRow() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        char otherChessRow = 2;
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);
        ChessPosition comparingPosition = ChessPosition.buildPositionFor(chessColumn, otherChessRow);

        // when
        boolean comparationResult = position.equals(comparingPosition);

        // then
        assertFalse(comparationResult, format("Compared piece suppose to be placed on a different chessRow '%s', but was placed on the same!", chessRow));
    }

    @Test
    public void shouldReturnTrueOnCallingEqualsWhenComparedBoardPieceIsPlacedOnTheSameBoardPosition() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);
        ChessPosition comparingPosition = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        boolean comparationResult = position.equals(comparingPosition);

        // then
        assertTrue(comparationResult, format("Compared piece suppose to be placed on the same chess [collumn][row] '%s%s', but was different!", chessColumn, chessRow));
    }

    @Test
    public void shouldReturnedChessPositionHasEqualsChessColumnWhenCallingClonePosition() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        ChessPosition clonedPosition = position.clonePosition();

        // then
        assertEquals(chessColumn, clonedPosition.getChessColumn(), format("Cloned position suppose to have same chessColumn %s, but was different %s!", chessColumn, clonedPosition.getChessColumn()));
    }

    @Test
    public void shouldReturnedChessPositionHasEqualsChessRowWhenCallingClonePosition() {
        // given
        int chessRow = 1;
        char chessColumn = 'a';
        ChessPosition position = ChessPosition.buildPositionFor(chessColumn, chessRow);

        // when
        ChessPosition clonedPosition = position.clonePosition();

        // then
        assertEquals(chessRow, clonedPosition.getChessRow(), format("Cloned position suppose to have same chessRow %s, but was different %s!", chessRow, clonedPosition.getChessRow()));
    }

}
