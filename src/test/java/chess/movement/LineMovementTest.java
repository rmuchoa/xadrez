package chess.movement;

import static chess.Color.BLACK;
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
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import chess.dummy.DummyChessPiece;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class LineMovementTest {

    private static final Integer NONE = 0;
    private static final Integer SINGLE = 1;

    @Mock
    private ChessBoard board;
    @Mock
    private ChessPiece piece;
    @Mock
    private ChessPosition position;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldBuildNorthMovementFromA1ChessPositionToA2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        when(piece.getPosition()).thenReturn(position);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, currentPosition, NORTH);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldBuildSouthMovementFromA3ChessPositionToA2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 3);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        when(piece.getPosition()).thenReturn(position);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, currentPosition, SOUTH);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldBuildEastMovementFromA2ChessPositionToB2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('a', 2);
        ChessPosition expectedPosition = new ChessPosition('b', 2);
        when(piece.getPosition()).thenReturn(position);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, currentPosition, EAST);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldBuildWestMovementFromC2ChessPositionToB2ChessPosition() {
        // given
        ChessPosition currentPosition = new ChessPosition('c', 2);
        ChessPosition expectedPosition = new ChessPosition('b', 2);
        when(piece.getPosition()).thenReturn(position);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, currentPosition, WEST);

        // then
        assertEquals(expectedPosition, movement.getTarget(), format("Movement target suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, movement.getTarget()));
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingLineMovementWithNortheastDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 1);
        when(piece.getPosition()).thenReturn(position);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            LineMovement.buildMovement(piece, currentPosition, NORTHEAST);
        });

        // then
        assertEquals("Invalid NORTHEAST direction for LineMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingLineMovementWithNorthwestDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 1);
        when(piece.getPosition()).thenReturn(position);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            LineMovement.buildMovement(piece, currentPosition, NORTHWEST);
        });

        // then
        assertEquals("Invalid NORTHWEST direction for LineMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingLineMovementWithSoutheastDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 2);
        when(piece.getPosition()).thenReturn(position);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            LineMovement.buildMovement(piece, currentPosition, SOUTHEAST);
        });

        // then
        assertEquals("Invalid SOUTHEAST direction for LineMovement", thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenBuildingLineMovementWithSouthwestDirection() {
        // given
        ChessPosition currentPosition = new ChessPosition('b', 2);
        when(piece.getPosition()).thenReturn(position);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            LineMovement.buildMovement(piece, currentPosition, SOUTHWEST);
        });

        // then
        assertEquals("Invalid SOUTHWEST direction for LineMovement", thrown.getMessage());
    }

    @Test
    public void shouldBuildNorthMovementFromA2ToA3WhenBuildingNextMovementForA1ToA2NorthLineMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition targetPosition = new ChessPosition('a', 2);
        ChessPosition expectedTargetPosition = new ChessPosition('a', 3);
        when(piece.getPosition()).thenReturn(sourcePosition);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, sourcePosition, NORTH);
        ChessMovement nextMovement = movement.buildNextMovement();

        // then
        assertEquals(sourcePosition, movement.getSource(), format("Current movement source position suppose to be a1, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, movement.getSource()));
        assertEquals(targetPosition, movement.getTarget(), format("Current movement target position suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", targetPosition, movement.getTarget()));
        assertEquals(sourcePosition, nextMovement.getSource(), format("Next movement source position suppose to be a1, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, nextMovement.getSource()));
        assertEquals(expectedTargetPosition, nextMovement.getTarget(), format("Next movement target position suppose to be a3, but wasn't. expected[%s] targetPosition[%s]", expectedTargetPosition, nextMovement.getTarget()));
    }

    @Test
    public void shouldBuildSouthMovementFromB2ToB1WhenBuildingNextMovementForB3ToB2SouthLineMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('b', 3);
        ChessPosition targetPosition = new ChessPosition('b', 2);
        ChessPosition expectedTargetPosition = new ChessPosition('b', 1);
        when(piece.getPosition()).thenReturn(sourcePosition);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, sourcePosition, SOUTH);
        ChessMovement nextMovement = movement.buildNextMovement();

        // then
        assertEquals(sourcePosition, movement.getSource(), format("Current movement source position suppose to be b3, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, movement.getSource()));
        assertEquals(targetPosition, movement.getTarget(), format("Current movement target position suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", targetPosition, movement.getTarget()));
        assertEquals(sourcePosition, nextMovement.getSource(), format("Next movement source position suppose to be b2, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, nextMovement.getSource()));
        assertEquals(expectedTargetPosition, nextMovement.getTarget(), format("Next movement target position suppose to be b1, but wasn't. expected[%s] targetPosition[%s]", expectedTargetPosition, nextMovement.getTarget()));
    }

    @Test
    public void shouldBuildEastMovementFromA2ToB2WhenBuildingNextMovementForB2ToC2EastLineMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 2);
        ChessPosition targetPosition = new ChessPosition('b', 2);
        ChessPosition expectedTargetPosition = new ChessPosition('c', 2);
        when(piece.getPosition()).thenReturn(sourcePosition);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, sourcePosition, EAST);
        ChessMovement nextMovement = movement.buildNextMovement();

        // then
        assertEquals(sourcePosition, movement.getSource(), format("Current movement source position suppose to be a2, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, movement.getSource()));
        assertEquals(targetPosition, movement.getTarget(), format("Current movement target position suppose to be b2, but wasn't. expected[%s] targetPosition[%s]", targetPosition, movement.getTarget()));
        assertEquals(sourcePosition, nextMovement.getSource(), format("Next movement source position suppose to be b2, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, nextMovement.getSource()));
        assertEquals(expectedTargetPosition, nextMovement.getTarget(), format("Next movement target position suppose to be c2, but wasn't. expected[%s] targetPosition[%s]", expectedTargetPosition, nextMovement.getTarget()));
    }

    @Test
    public void shouldBuildWestMovementFromD1ToC1WhenBuildingNextMovementForC1ToB1WestLineMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('d', 1);
        ChessPosition targetPosition = new ChessPosition('c', 1);
        ChessPosition expectedTargetPosition = new ChessPosition('b', 1);
        when(piece.getPosition()).thenReturn(sourcePosition);

        // when
        LineMovement movement = LineMovement.buildMovement(piece, sourcePosition, WEST);
        ChessMovement nextMovement = movement.buildNextMovement();

        // then
        assertEquals(sourcePosition, movement.getSource(), format("Current movement source position suppose to be d1, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, movement.getSource()));
        assertEquals(targetPosition, movement.getTarget(), format("Current movement target position suppose to be c1, but wasn't. expected[%s] targetPosition[%s]", targetPosition, movement.getTarget()));
        assertEquals(sourcePosition, nextMovement.getSource(), format("Next movement source position suppose to be c1, but wasn't. expected[%s] sourcePosition[%s]", sourcePosition, nextMovement.getSource()));
        assertEquals(expectedTargetPosition, nextMovement.getTarget(), format("Next movement target position suppose to be b1, but wasn't. expected[%s] targetPosition[%s]", expectedTargetPosition, nextMovement.getTarget()));
    }

    @Test
    public void shouldLineMovementFindJustOneNorthMovementWhenCheckingForSingleNorthMovementFromA1Position() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).build();
        when(board.doesExists(expectedPosition)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition)).thenReturn(false);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(piece);

        // when
        List<ChessMovement> availableMovements = LineMovement.checkSingleMovement(piece, NORTH);

        // then
        assertEquals(SINGLE, availableMovements.size(), "North available movements suppose to be just a single one.");
        assertEquals(expectedPosition, availableMovements.get(0).getTarget(), format("Current movement target position suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition, availableMovements.get(0).getTarget()));
    }

    @Test
    public void shouldLineMovementFindNoOneNorthMovementWhenCheckingForSingleNorthMovementFromA8PositionToA9NotExistentPosition() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 8);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).build();

        // when
        List<ChessMovement> availableMovements = LineMovement.checkSingleMovement(piece, NORTH);

        // then
        assertEquals(NONE, availableMovements.size(), "North available movements suppose to found not a single one.");
    }

    @Test
    public void shouldLineMovementFindNoOneNorthMovementWhenCheckingForSingleNorthMovementFromA1PositionAndExpectedPositionIsNotAnEmptyPosition() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition = new ChessPosition('a', 2);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).build();
        when(board.doesExists(expectedPosition)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition)).thenReturn(false);

        // when
        List<ChessMovement> availableMovements = LineMovement.checkSingleMovement(piece, NORTH);

        // then
        assertEquals(NONE, availableMovements.size(), "North available movements suppose to found not a single one.");
    }

    @Test
    public void shouldLineMovementFindTwoNorthMovementsBeforeReachOutOfBoardWhenCheckingForNorthMovementsFromA1Position() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition1 = new ChessPosition('a', 2);
        ChessPosition expectedPosition2 = new ChessPosition('a', 3);
        ChessPosition expectedPosition3 = new ChessPosition('a', 4);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).build();
        when(board.doesExists(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition1)).thenReturn(false);
        when(board.doesExists(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition2)).thenReturn(false);
        when(board.doesExists(expectedPosition3)).thenReturn(false);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(piece);

        // when
        List<ChessMovement> availableMovements = LineMovement.checkMovements(piece, NORTH);

        // then
        assertEquals(2, availableMovements.size(), "North available movements suppose to be two.");
        assertEquals(expectedPosition1, availableMovements.get(0).getTarget(), format("First target position suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition1, availableMovements.get(0).getTarget()));
        assertEquals(expectedPosition2, availableMovements.get(1).getTarget(), format("Second target position suppose to be a3, but wasn't. expected[%s] targetPosition[%s]", expectedPosition2, availableMovements.get(1).getTarget()));
    }

    @Test
    public void shouldLineMovementFindTwoNorthMovementsBeforeReachAnotherMatePieceWhenCheckingForNorthMovementsFromA1Position() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition1 = new ChessPosition('a', 2);
        ChessPosition expectedPosition2 = new ChessPosition('a', 3);
        ChessPosition expectedPosition3 = new ChessPosition('a', 4);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).color(Color.WHITE).position(sourcePosition).build();
        DummyChessPiece matePiece = DummyChessPiece.builder().board(board).color(Color.WHITE).position(expectedPosition3).build();
        when(board.doesExists(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition1)).thenReturn(false);
        when(board.doesExists(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition2)).thenReturn(false);
        when(board.doesExists(expectedPosition3)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition3)).thenReturn(false);
        when(board.isBoardPositionOccupied(expectedPosition3)).thenReturn(true);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(piece);
        when(board.getPiecePlacedOn(expectedPosition3)).thenReturn(matePiece);

        // when
        List<ChessMovement> availableMovements = LineMovement.checkMovements(piece, NORTH);

        // then
        assertEquals(2, availableMovements.size(), "North available movements suppose to be two.");
        assertEquals(expectedPosition1, availableMovements.get(0).getTarget(), format("First target position suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition1, availableMovements.get(0).getTarget()));
        assertEquals(expectedPosition2, availableMovements.get(1).getTarget(), format("Second target position suppose to be a3, but wasn't. expected[%s] targetPosition[%s]", expectedPosition2, availableMovements.get(1).getTarget()));
    }

    @Test
    public void shouldLineMovementFindThreeNorthMovementsReachSomeOpponentPieceWhenCheckingForNorthMovementsFromA1Position() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition expectedPosition1 = new ChessPosition('a', 2);
        ChessPosition expectedPosition2 = new ChessPosition('a', 3);
        ChessPosition expectedPosition3 = new ChessPosition('a', 4);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).color(Color.WHITE).position(sourcePosition).build();
        DummyChessPiece opponentPiece = DummyChessPiece.builder().board(board).color(Color.BLACK).position(expectedPosition3).build();
        when(board.doesExists(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition1)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition1)).thenReturn(false);
        when(board.doesExists(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition2)).thenReturn(true);
        when(board.isBoardPositionOccupied(expectedPosition2)).thenReturn(false);
        when(board.doesExists(expectedPosition3)).thenReturn(true);
        when(board.isBoardPositionEmpty(expectedPosition3)).thenReturn(false);
        when(board.isBoardPositionOccupied(expectedPosition3)).thenReturn(true);
        when(board.getPiecePlacedOn(sourcePosition)).thenReturn(piece);
        when(board.getPiecePlacedOn(expectedPosition3)).thenReturn(opponentPiece);

        // when
        List<ChessMovement> availableMovements = LineMovement.checkMovements(piece, NORTH);

        // then
        assertEquals(3, availableMovements.size(), "North available movements suppose to be three.");
        assertEquals(expectedPosition1, availableMovements.get(0).getTarget(), format("First target position suppose to be a2, but wasn't. expected[%s] targetPosition[%s]", expectedPosition1, availableMovements.get(0).getTarget()));
        assertEquals(expectedPosition2, availableMovements.get(1).getTarget(), format("Second target position suppose to be a3, but wasn't. expected[%s] targetPosition[%s]", expectedPosition2, availableMovements.get(1).getTarget()));
        assertEquals(expectedPosition3, availableMovements.get(2).getTarget(), format("Third target position suppose to be a4, but wasn't. expected[%s] targetPosition[%s]", expectedPosition3, availableMovements.get(2).getTarget()));
    }

    @Test
    public void shouldLineMovementFindNoOneNorthMovementWhenCheckingForNorthMovementsFromA8PositionToNotExistentPositionsBeyond() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 8);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).build();

        // when
        List<ChessMovement> availableMovements = LineMovement.checkMovements(piece, NORTH);

        // then
        assertEquals(NONE, availableMovements.size(), "North available movements suppose to found not a single one.");
    }

    @Test
    public void shouldDoNothingWhenAskingToLineMovementDoComposedMove() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).build();
        DummyChessPiece anotherPiece = DummyChessPiece.builder().board(board).position(sourcePosition).build();

        // when
        LineMovement movement = LineMovement.buildMovement(piece, sourcePosition, NORTH);
        assertDoesNotThrow(() -> {
            movement.doComposedMove(anotherPiece);
        });
    }

    @Test
    public void shouldDoNothingWhenAskingToLineMovementUndoComposedMove() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).build();
        DummyChessPiece anotherPiece = DummyChessPiece.builder().board(board).position(sourcePosition).build();

        // when
        LineMovement movement = LineMovement.buildMovement(piece, sourcePosition, NORTH);
        assertDoesNotThrow(() -> {
            movement.undoComposedMove(anotherPiece);
        });
    }

    @Test
    public void shouldCloneMovement() {
        // given
        ChessPosition sourcePosition = new ChessPosition('a', 1);
        ChessPosition targetPosition = new ChessPosition('a', 2);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).position(sourcePosition).color(BLACK).build();
        DummyChessPiece clonedPiece = DummyChessPiece.builder().board(board).position(sourcePosition).color(BLACK).build();

        // when
        LineMovement movement = LineMovement.buildMovement(piece, sourcePosition, NORTH);
        LineMovement clonedMovement = (LineMovement) movement.cloneMovement(clonedPiece);

        // then
        assertEquals(clonedPiece, clonedMovement.getPiece());
        assertEquals(sourcePosition, clonedMovement.getSource());
        assertEquals(targetPosition, clonedMovement.getTarget());
        assertEquals(NORTH, clonedMovement.getDirection());
    }

}
