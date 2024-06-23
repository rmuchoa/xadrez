package chess.movement;

import static chess.Color.BLACK;
import static chess.Color.WHITE;
import static chess.movement.types.MovementDirection.EAST;
import static chess.movement.types.MovementDirection.NORTH;
import static chess.movement.types.MovementDirection.NORTHEAST;
import static chess.movement.types.MovementDirection.NORTHWEST;
import static chess.movement.types.MovementDirection.SOUTH;
import static chess.movement.types.MovementDirection.SOUTHEAST;
import static chess.movement.types.MovementDirection.SOUTHWEST;
import static chess.movement.types.MovementDirection.WEST;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessMovement;
import chess.ChessPosition;
import chess.Color;
import chess.dummy.DummyChessPiece;
import chess.dummy.DummyPawn;
import chess.pieces.Pawn;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class PawnWalkMovementTest {

    @Mock
    private ChessBoard board;
    @Mock
    private Pawn pawn;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldBuildNorthPawnWalkMovementFromA1ChessPositionToA2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn, currentPosition, NORTH);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldBuildSouthPawnWalkMovementFromA3ChessPositionToA2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 3);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn, currentPosition, SOUTH);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnWalkMovementWithPureEastDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 1);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnWalkMovement.buildMovement(pawn, currentPosition, EAST);
        });

        // then
        assertEquals("Invalid EAST direction for PawnWalkMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnWalkMovementWithPureWestDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 1);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnWalkMovement.buildMovement(pawn, currentPosition, WEST);
        });

        // then
        assertEquals("Invalid WEST direction for PawnWalkMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnWalkMovementWithNortheastDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 1);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnWalkMovement.buildMovement(pawn, currentPosition, NORTHEAST);
        });

        // then
        assertEquals("Invalid NORTHEAST direction for PawnWalkMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnWalkMovementWithNorthwestDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 1);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnWalkMovement.buildMovement(pawn, currentPosition, NORTHWEST);
        });

        // then
        assertEquals("Invalid NORTHWEST direction for PawnWalkMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnWalkMovementWithSoutheastDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 1);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnWalkMovement.buildMovement(pawn, currentPosition, SOUTHEAST);
        });

        // then
        assertEquals("Invalid SOUTHEAST direction for PawnWalkMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnWalkMovementWithSouthwestDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 2);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnWalkMovement.buildMovement(pawn, currentPosition, SOUTHWEST);
        });

        // then
        assertEquals("Invalid SOUTHWEST direction for PawnWalkMovement", thrown.getMessage());
    }

    @Test
    public void shouldBuildNorthPawnWalkMovementForWhiteColorPawnFromA1ChessPositionToA2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).position(currentPosition).board(board).build();

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
        assertEquals(NORTH, movement.getDirection(), format("Movement direction suppose to be NORTH, but wasn't. direction[%s]", movement.getDirection()));
    }

    @Test
    public void shouldBuildSouthPawnWalkMovementForBlackColorPawnFromA5ChessPositionToA4ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 5);
        ChessPosition expectedPosition = new ChessPosition('a', 4);
        DummyPawn pawn = DummyPawn.builder().color(BLACK).position(currentPosition).board(board).build();

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be a4, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
        assertEquals(SOUTH, movement.getDirection(), format("Movement direction suppose to be SOUTH, but wasn't. direction[%s]", movement.getDirection()));
    }

    @Test
    public void shouldBuildNortheastMovementFromA2ToA3WhenBuildingNextMovementForA1ToA2NortheastDiagonalMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition targetPosition = new ChessPosition('a', 2);
        ChessPosition expectedTargetPosition = new ChessPosition('a', 3);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).position(sourcePosition).board(board).build();

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn);
        PawnWalkMovement nextMovement = (PawnWalkMovement) movement.buildNextMovement();

        // then
        assertEquals(sourcePosition, movement.getSource(), format("Current movement source position suppose to be a1, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, movement.getSource()));
        assertEquals(targetPosition, movement.getTarget(), format("Current movement target position suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", targetPosition, movement.getTarget()));
        assertEquals(sourcePosition, nextMovement.getSource(), format("Next movement source position suppose to be a1, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, nextMovement.getSource()));
        assertEquals(expectedTargetPosition, nextMovement.getTarget(), format("Next movement target position suppose to be a3, but wasn't. expected[%s] targetPosition[%s]", expectedTargetPosition, nextMovement.getTarget()));
        assertEquals(NORTH, movement.getDirection(), format("Movement direction suppose to be NORTH, but wasn't. direction[%s]", movement.getDirection()));
        assertEquals(NORTH, nextMovement.getDirection(), format("Movement direction suppose to be NORTH, but wasn't. direction[%s]", movement.getDirection()));
    }

    @Test
    public void shouldPawnWalkMovementFindTwoNorthMovementsWhenCheckingForNorthMovementsFromA2PositionForNotYetMovedWhitePawnPiece() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 2);
        ChessPosition expectedPosition1 = new ChessPosition('a', 3);
        ChessPosition expectedPosition2 = new ChessPosition('a', 4);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        when(board.doesExists(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition1)).thenReturn(false);
        when(board.doesExists(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition2)).thenReturn(false);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(pawn);

        // when
        List<ChessMovement> availableMovements = PawnWalkMovement.checkWalkMovements(pawn);

        // then
        assertEquals(2, availableMovements.size(), "North available movements suppose to be two.");
        assertEquals(expectedPosition1, availableMovements.get(0).getTarget(), format("First target position suppose to be a3, but wasn't. expected[%s] targetPosition[%s]", expectedPosition1, availableMovements.get(0).getTarget()));
        assertEquals(expectedPosition2, availableMovements.get(1).getTarget(), format("Second target position suppose to be a4, but wasn't. expected[%s] targetPosition[%s]", expectedPosition2, availableMovements.get(1).getTarget()));
        assertEquals(NORTH, ((PawnWalkMovement) availableMovements.get(0)).getDirection(), format("First movement direction suppose to be NORTH, but wasn't. direction[%s]", ((PawnWalkMovement) availableMovements.get(0)).getDirection()));
        assertEquals(NORTH, ((PawnWalkMovement) availableMovements.get(1)).getDirection(), format("Second movement direction suppose to be NORTH, but wasn't. direction[%s]", ((PawnWalkMovement) availableMovements.get(1)).getDirection()));
    }

    @Test
    public void shouldPawnWalkMovementFindTwoSouthMovementsWhenCheckingForSouthMovementsFromA7PositionForNotYetMovedBlackPawnPiece() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 7);
        ChessPosition expectedPosition1 = new ChessPosition('a', 6);
        ChessPosition expectedPosition2 = new ChessPosition('a', 5);
        DummyPawn pawn = DummyPawn.builder().color(BLACK).board(board).position(sourcePosition).build();
        when(board.doesExists(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition1)).thenReturn(false);
        when(board.doesExists(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition2)).thenReturn(false);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(pawn);

        // when
        List<ChessMovement> availableMovements = PawnWalkMovement.checkWalkMovements(pawn);

        // then
        assertEquals(2, availableMovements.size(), "South available movements suppose to be two.");
        assertEquals(expectedPosition1, availableMovements.get(0).getTarget(), format("First target position suppose to be a6, but wasn't. expected[%s] targetPosition[%s]", expectedPosition1, availableMovements.get(0).getTarget()));
        assertEquals(expectedPosition2, availableMovements.get(1).getTarget(), format("Second target position suppose to be a5, but wasn't. expected[%s] targetPosition[%s]", expectedPosition2, availableMovements.get(1).getTarget()));
        assertEquals(SOUTH, ((PawnWalkMovement) availableMovements.get(0)).getDirection(), format("First movement direction suppose to be NORTH, but wasn't. direction[%s]", ((PawnWalkMovement) availableMovements.get(0)).getDirection()));
        assertEquals(SOUTH, ((PawnWalkMovement) availableMovements.get(1)).getDirection(), format("Second movement direction suppose to be NORTH, but wasn't. direction[%s]", ((PawnWalkMovement) availableMovements.get(1)).getDirection()));
    }

    @Test
    public void shouldPawnWalkMovementFindSingleNorthMovementWhenCheckingForNorthMovementsFromA4PositionForAlreadyMovedWhitePawnPiece() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 4);
        ChessPosition expectedPosition1 = new ChessPosition('a', 5);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        pawn.increaseMoveCount();
        when(board.doesExists(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition1)).thenReturn(false);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(pawn);

        // when
        List<ChessMovement> availableMovements = PawnWalkMovement.checkWalkMovements(pawn);

        // then
        assertEquals(1, availableMovements.size(), "North available movements suppose to be a single one.");
        assertEquals(expectedPosition1, availableMovements.get(0).getTarget(), format("First target position suppose to be a5, but wasn't. expected[%s] targetPosition[%s]", expectedPosition1, availableMovements.get(0).getTarget()));
    }

    @Test
    public void shouldPawnWalkMovementFindSingleSouthMovementWhenCheckingForSouthMovementsFromA4PositionForAlreadyMovedBlackPawnPiece() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 4);
        ChessPosition expectedPosition1 = new ChessPosition('a', 3);
        DummyPawn pawn = DummyPawn.builder().color(BLACK).board(board).position(sourcePosition).build();
        pawn.increaseMoveCount();
        when(board.doesExists(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition1)).thenReturn(false);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(pawn);

        // when
        List<ChessMovement> availableMovements = PawnWalkMovement.checkWalkMovements(pawn);

        // then
        assertEquals(1, availableMovements.size(), "South available movements suppose to be a single one.");
        assertEquals(expectedPosition1, availableMovements.get(0).getTarget(), format("First target position suppose to be a3, but wasn't. expected[%s] targetPosition[%s]", expectedPosition1, availableMovements.get(0).getTarget()));
    }

    @Test
    public void shouldDoNothingWhenAskingToPawnTakeMovementDoComposedMove() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece anotherPiece = DummyChessPiece.builder().board(board).position(sourcePosition).build();

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn);
        assertDoesNotThrow(() -> {
            movement.doComposedMove(anotherPiece);
        });
    }

    @Test
    public void shouldDoNothingWhenAskingToPawnTakeMovementUndoComposedMove() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece anotherPiece = DummyChessPiece.builder().board(board).position(sourcePosition).build();

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn);
        assertDoesNotThrow(() -> {
            movement.undoComposedMove(anotherPiece);
        });
    }

    @Test
    public void shouldCloneMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition targetPosition = new ChessPosition('a', 2);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece clonedPiece = DummyChessPiece.builder().board(board).position(sourcePosition).color(WHITE).build();

        // when
        PawnWalkMovement movement = PawnWalkMovement.buildMovement(pawn);
        PawnWalkMovement clonedMovement = (PawnWalkMovement) movement.cloneMovement(pawn);

        // then
        assertEquals(clonedPiece, clonedMovement.getPiece());
        assertEquals(sourcePosition, clonedMovement.getSource());
        assertEquals(targetPosition, clonedMovement.getTarget());
        assertEquals(NORTH, clonedMovement.getDirection());
    }

}
