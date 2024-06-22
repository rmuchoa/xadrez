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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import chess.ChessBoard;
import chess.ChessException;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import chess.dummy.DummyChessPiece;
import chess.dummy.DummyPawn;
import chess.pieces.Pawn;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class PawnTakeMovementTest {

    private static final Integer NONE = 0;
    private static final Integer SINGLE = 1;

    @Mock
    private ChessBoard board;
    @Mock
    private Pawn pawn;
    @Mock
    private ChessPosition position;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldBuildNortheastPawnTakeMovementFromA1ChessPositionToB2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('b', 2);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        PawnTakeMovement movement = PawnTakeMovement.buildMovement(pawn, NORTHEAST);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldBuildNorthwestPawnTakeMovementFromB1ChessPositionToA2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 1);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        PawnTakeMovement movement = PawnTakeMovement.buildMovement(pawn, NORTHWEST);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnTakeMovementWithPureNorthDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 1);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnTakeMovement.buildMovement(pawn, NORTH);
        });

        // then
        assertEquals("Invalid NORTH direction for PawnTakeMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnTakeMovementWithPureSouthDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 1);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnTakeMovement.buildMovement(pawn, SOUTH);
        });

        // then
        assertEquals("Invalid SOUTH direction for PawnTakeMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnTakeMovementWithPureEastDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 2);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnTakeMovement.buildMovement(pawn, EAST);
        });

        // then
        assertEquals("Invalid EAST direction for PawnTakeMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingPawnTakeMovementWithPureWestDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 2);
        when(pawn.getPosition()).thenReturn(currentPosition);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            PawnTakeMovement.buildMovement(pawn, WEST);
        });

        // then
        assertEquals("Invalid WEST direction for PawnTakeMovement", thrown.getMessage());
    }

    @Test
    public void shouldBuildNullMovementWhenBuildingNextMovementForPawnTakeMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition targetPosition = new ChessPosition('b', 2);
        when(pawn.getPosition()).thenReturn(sourcePosition);

        // when
        PawnTakeMovement movement = PawnTakeMovement.buildMovement(pawn, NORTHEAST);
        ChessMovement nextMovement = movement.buildNextMovement();

        // then
        assertEquals(sourcePosition, movement.getSource(), format("Current movement source position suppose to be a1, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, movement.getSource()));
        assertEquals(targetPosition, movement.getTarget(), format("Current movement target position suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", targetPosition, movement.getTarget()));
        assertNull(nextMovement, format("Next movement source position suppose to be null, but wasn't. nextMovement[%s]", nextMovement));
    }

    @Test
    public void shouldFindJustOneNortheastPawnTakeMovementWhenCheckingForSingleNortheastMovementFromA1Position() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('b', 2);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece opponentPiece = DummyChessPiece.builder().board(board).color(Color.BLACK).position(expectedPosition).build();
        when(board.doesExists(expectedPosition)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition)).thenReturn(false);
        when(board.isBoardPositionOccupied(expectedPosition)).thenReturn(true);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(pawn);
        when(board.getPiecePlacedOn(expectedPosition)).thenReturn(opponentPiece);

        // when
        List<ChessMovement> availableMovements = PawnTakeMovement.checkTakeMovement(pawn, NORTHEAST);

        // then
        assertEquals(SINGLE, availableMovements.size(), "Northeast available movements suppose to be just a single one.");
        assertEquals(expectedPosition, availableMovements.get(0).getTarget(), format("Current movement target position suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, availableMovements.get(0).getTarget()));
    }

    @Test
    public void shouldFindJustOneNorthwestPawnTakeMovementWhenCheckingForSingleNorthwestMovementFromA1Position() {
        // given
        ChessPosition sourcePosition = new ChessPosition('b', 1);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece opponentPiece = DummyChessPiece.builder().board(board).color(Color.BLACK).position(expectedPosition).build();
        when(board.doesExists(expectedPosition)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition)).thenReturn(false);
        when(board.isBoardPositionOccupied(expectedPosition)).thenReturn(true);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(pawn);
        when(board.getPiecePlacedOn(expectedPosition)).thenReturn(opponentPiece);

        // when
        List<ChessMovement> availableMovements = PawnTakeMovement.checkTakeMovement(pawn, NORTHWEST);

        // then
        assertEquals(SINGLE, availableMovements.size(), "Northeast available movements suppose to be just a single one.");
        assertEquals(expectedPosition, availableMovements.get(0).getTarget(), format("Current movement target position suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, availableMovements.get(0).getTarget()));
    }

    @Test
    public void shouldFindNoOneNortheastPawnTakeMovementWhenCheckingForSingleNortheastMovementForWhitePiece() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 3);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();

        // when
        List<ChessMovement> availableMovements = PawnTakeMovement.checkTakeMovement(pawn, SOUTHEAST);

        // then
        assertEquals(NONE, availableMovements.size(), "Northeast available movements suppose to found not a single one.");
    }

    @Test
    public void shouldFindNoOneSoutheastPawnTakeMovementWhenCheckingForSingleNortheastMovementForBlackPiece() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 3);
        DummyPawn pawn = DummyPawn.builder().color(BLACK).board(board).position(sourcePosition).build();

        // when
        List<ChessMovement> availableMovements = PawnTakeMovement.checkTakeMovement(pawn, NORTHEAST);

        // then
        assertEquals(NONE, availableMovements.size(), "Northeast available movements suppose to found not a single one.");
    }

    @Test
    public void shouldFindNoOneNortheastPawnTakeMovementWhenCheckingForSingleNortheastMovementToTakeSomeMatePiece() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('b', 2);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece matePiece = DummyChessPiece.builder().board(board).color(WHITE).position(expectedPosition).build();
        when(board.doesExists(expectedPosition)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition)).thenReturn(false);
        when(board.isBoardPositionOccupied(expectedPosition)).thenReturn(true);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(pawn);
        when(board.getPiecePlacedOn(expectedPosition)).thenReturn(matePiece);

        // when
        List<ChessMovement> availableMovements = PawnTakeMovement.checkTakeMovement(pawn, NORTHEAST);

        // then
        assertEquals(NONE, availableMovements.size(), "Northeast available movements suppose to found not a single one.");
    }

    @Test
    public void shouldDoNothingWhenAskingToPawnTakeMovementDoComposedMove() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece anotherPiece = DummyChessPiece.builder().board(board).position(sourcePosition).build();

        // when
        PawnTakeMovement movement = PawnTakeMovement.buildMovement(pawn, NORTHEAST);
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
        PawnTakeMovement movement = PawnTakeMovement.buildMovement(pawn, NORTHEAST);
        assertDoesNotThrow(() -> {
            movement.undoComposedMove(anotherPiece);
        });
    }

    @Test
    public void shouldCloneMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition targetPosition = new ChessPosition('b', 2);
        DummyPawn pawn = DummyPawn.builder().color(WHITE).board(board).position(sourcePosition).build();
        DummyChessPiece clonedPiece = DummyChessPiece.builder().board(board).position(sourcePosition).color(WHITE).build();

        // when
        PawnTakeMovement movement = PawnTakeMovement.buildMovement(pawn, NORTHEAST);
        ChessMovement clonedMovement = movement.cloneMovement(pawn);

        // then
        assertEquals(clonedPiece, clonedMovement.getPiece());
        assertEquals(sourcePosition, clonedMovement.getSource());
        assertEquals(targetPosition, clonedMovement.getTarget());
        assertEquals(NORTHEAST, ((PawnTakeMovement) clonedMovement).getDirection());
    }

}
