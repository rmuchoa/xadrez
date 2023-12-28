package board;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardPositionTest {

    @Test
    public void shouldBoardPositionsBeEqualsWhenMatrixRowAndMatrixColumnAreBothEqual() {
        // given
        int matrixRow = 10;
        int matrixColumn = 10;
        BoardPosition position1 = new BoardPosition(matrixRow, matrixColumn);
        BoardPosition position2 = new BoardPosition(matrixRow, matrixColumn);

        // when
        boolean equalsResult = position1.equals(position2);

        // then
        assertTrue(equalsResult, String.format("Positions are not equals! %s, %s", position1, position2));
    }

    @Test
    public void shouldBoardPositionsBeDifferentWhenSecondPositionIsNull() {
        // given
        int matrixRow = 10;
        int matrixColumn = 10;
        BoardPosition position1 = new BoardPosition(matrixRow, matrixColumn);
        BoardPosition position2 = null;

        // when
        boolean equalsResult = position1.equals(position2);

        // then
        assertFalse(equalsResult, String.format("Positions should not be considered equals! %s, %s", position1, position2));
    }

    @Test
    public void shouldBoardPositionsBeDifferentWhenMatrixRowAreDifferentBetweenBothPositions() {
        // given
        int matrixRow = 10;
        int matrixColumn = 10;
        BoardPosition position1 = new BoardPosition(matrixRow, matrixColumn);
        BoardPosition position2 = new BoardPosition(++matrixRow, matrixColumn);

        // when
        boolean equalsResult = position1.equals(position2);

        // then
        assertFalse(equalsResult, String.format("Positions should not be considered equals! %s, %s", position1, position2));
    }

    @Test
    public void shouldBoardPositionsBeDifferentWhenMatrixColumnAreDifferentBetweenBothPositions() {
        // given
        int matrixRow = 10;
        int matrixColumn = 10;
        BoardPosition position1 = new BoardPosition(matrixRow, matrixColumn);
        BoardPosition position2 = new BoardPosition(matrixRow, ++matrixColumn);

        // when
        boolean equalsResult = position1.equals(position2);

        // then
        assertFalse(equalsResult, String.format("Positions should not be considered equals! %s, %s", position1, position2));
    }

    @Test
    public void shouldBoardPositionsBeDifferentWhenMatrixRowAndMatrixColumnAreBothDifferentBetweenPositions() {
        // given
        int matrixRow = 10;
        int matrixColumn = 10;
        BoardPosition position1 = new BoardPosition(matrixRow, matrixColumn);
        BoardPosition position2 = new BoardPosition(++matrixRow, ++matrixColumn);

        // when
        boolean equalsResult = position1.equals(position2);

        // then
        assertFalse(equalsResult, String.format("Positions should not be considered equals! %s, %s", position1, position2));
    }

}
