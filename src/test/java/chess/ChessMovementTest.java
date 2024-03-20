package chess;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.mockito.Mockito.verify;

import chess.dummy.DummyChessMovement;
import chess.dummy.DummyChessPiece;
import chess.movement.types.MovementDirection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ChessMovementTest {

    private static final int TWICE = 2;

    @Mock
    private ChessPiece piece;
    @Mock
    private ChessBoard board;
    @Mock
    private ChessPosition source;
    @Mock
    private ChessPosition target;
    @Mock
    private ChessPiece sourcePiece;
    @Mock
    private ChessPiece targetPiece;
    @Mock
    private ChessMovement nextMovement;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldReturnEmptyListWhenCheckingMovementsForNullChessMovement() {
        // given
        Integer expectedSize = 0;
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        List<ChessMovement> movements = movement.checkMovements(null, null, null);

        // then
        assertEquals(expectedSize, movements.size(), format("Movement list checked suppose to be empty, but wasn't. Movement size [%s]", movements.size()));
    }

    @Test
    public void shouldReturnEmptyListWhenCheckingMovementsForMovementThatTargetPositionDoesNotExists() {
        // given
        Integer expectedSize = 0;
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(false);

        // when
        List<ChessMovement> movements = movement.checkMovements(2);

        // then
        assertEquals(expectedSize, movements.size(), format("Movement list checked suppose to be empty, but wasn't. Movement size [%s]", movements.size()));
    }

    @Test
    public void shouldReturnEmptyListWhenCheckingMovementsForMovementThatTargetPositionIsNotEmptyAndHasNotAnOpponentPlacedThere() {
        // given
        Integer expectedSize = 0;
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(false);

        // when
        List<ChessMovement> movements = movement.checkMovements(2);

        // then
        assertEquals(expectedSize, movements.size(), format("Movement list checked suppose to be empty, but wasn't. Movement size [%s]", movements.size()));
    }

    @Test
    public void shouldReturnSingleMovementWhenCheckingMovementsForMovementThatTargetPositionIsOccupiedAndHasOpponentPlacedThere() {
        // given
        Integer expectedSize = 1;
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(true);

        // when
        List<ChessMovement> movements = movement.checkMovements(2);

        // then
        assertEquals(expectedSize, movements.size(), format("Checked movement list suppose to had just a single movement, but had more than that. Movement size [%s]", movements.size()));
        assertEquals(movement, movements.getFirst(), format("Returned single movement suppose to be exactly the sent movement, but wasn't. Was a different one movement[%s] first returned[%s]", movement, movements.getFirst()));
    }

    @Test
    public void shouldReturnSingleMovementWhenCheckingMovementsForMovementThatTargetPositionIsNotOccupiedAndDurationNotAllowMoveFreelyAndNotAllowMoreMovements() {
        // given
        Integer expectedSize = 1;
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(false);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);

        // when
        List<ChessMovement> movements = movement.checkMovements(1);

        // then
        assertEquals(expectedSize, movements.size(), format("Checked movement list suppose to had just a single movement, but had more than that. Movement size [%s]", movements.size()));
        assertEquals(movement, movements.getFirst(), format("Returned single movement suppose to be exactly the sent movement, but wasn't. Was a different one movement[%s] first returned[%s]", movement, movements.getFirst()));
    }

    @Test
    public void shouldReturnSingleMovementWhenCheckingMovementsForMovementThatTargetPositionIsNotOccupiedAndDurationAllowMoveFreely() {
        // given
        Integer expectedSize = 2;
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).nextMovement(nextMovement).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(false);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(movement.hasOpponentOnTargetPosition()).thenReturn(true);

        // when
        List<ChessMovement> movements = movement.checkMovements();

        // then
        assertEquals(expectedSize, movements.size(), format("Checked movement list suppose to had just a single movement, but had more than that. Movement size [%s]", movements.size()));
        assertEquals(movement, movements.getFirst(), format("Returned first movement suppose to be exactly the sent movement, but wasn't. Was a different one movement[%s] first returned[%s]", movement, movements.getFirst()));
        assertEquals(nextMovement, movements.getLast(), format("Returned last movement suppose to be exactly the next movement, but wasn't. Was a different one movement[%s] first returned[%s]", movement, movements.getLast()));
    }

    @Test
    public void shouldReturnSingleMovementWhenCheckingMovementsForMovementThatTargetPositionIsNotOccupiedAndDurationNotAllowMoveFreelyButAllowMoreMovementsForRemainingMovements() {
        // given
        Integer expectedSize = 3;
        ChessMovement lastMovement = mock(ChessMovement.class);
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).nextMovement(nextMovement).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(false);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(nextMovement.buildNextMovement()).thenReturn(lastMovement);

        // when
        List<ChessMovement> movements = movement.checkMovements(3);

        // then
        assertEquals(expectedSize, movements.size(), format("Checked movement list suppose to had just a single movement, but had more than that. Movement size [%s]", movements.size()));
        assertEquals(movement, movements.getFirst(), format("Returned first movement suppose to be exactly the sent movement, but wasn't. Was a different one movement[%s] first returned[%s]", movement, movements.getFirst()));
        assertEquals(nextMovement, movements.get(1), format("Returned second movement suppose to be exactly the sent movement, but wasn't. Was a different one nextMovement[%s] second returned[%s]", nextMovement, movements.get(1)));
        assertEquals(lastMovement, movements.getLast(), format("Returned third movement suppose to be exactly the next movement, but wasn't. Was a different one lastMovement[%s] third returned[%s]", lastMovement, movements.getLast()));
    }

    @Test
    public void shouldReturnZeroWhenTryingToDecreaseZeroRemainingSlots() {
        // given
        Integer expected = 0;
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        Integer decreasedSlots = movement.decreaseMovementDistance(0);

        // then
        assertEquals(expected, decreasedSlots, format("Decreased slots suppose to be the same quantity, but wasn't. expected[%s] decreasedSlots[%s]", expected, decreasedSlots));
    }

    @Test
    public void shouldReturnNullWhenTryingToDecreaseZeroRemainingSlots() {
        // given
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        Integer decreasedSlots = movement.decreaseMovementDistance(null);

        // then
        assertNull(decreasedSlots, format("Decreased slots suppose to be the same quantity, but wasn't. expected[null] decreasedSlots[%s]", decreasedSlots));
    }

    @Test
    public void shouldReturnDecreasedSlotsWhenDecreaseNonNullAndBiggerThanZeroRemainingSlots() {
        // given
        Integer expected = 1;
        Integer remainingSlots = 2;
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        Integer decreasedSlots = movement.decreaseMovementDistance(remainingSlots);

        // then
        assertEquals(expected, decreasedSlots, format("Decreased slots suppose to be really decreased, but wasn't. expected[%s] decreasedSlots[%s]", expected, decreasedSlots));
    }

    @Test
    public void shouldAskBoardIfTargetPositionDoesExistsWhenCallingMovementIsNotAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.isNotAvailableMovement();

        // then
        verify(board, times(TWICE)).doesExists(target);
    }

    @Test
    public void shouldAskBoardIfTargetPositionIsEmptyWhenCallingMovementIsNotAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);

        // when
        movement.isNotAvailableMovement();

        // then
        verify(board).isBoardPositionEmpty(target);
    }

    @Test
    public void shouldReturnFalseWhenMovementHasTargetPositionEmptyOnCallingIsNotAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(true);

        // when
        boolean isNotEmpty = movement.isNotAvailableMovement();

        // then
        assertFalse(isNotEmpty, format("Source piece suppose to has target position filled, but wasn't. isNotEmpty[%s]", isNotEmpty));
    }

    @Test
    public void shouldAskBoardIfTargetPositionIsOccupiedWhenTargetPositionIsNotEmptyOnCallingMovementIsNotAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);

        // when
        movement.isAvailableMovement();

        // then
        verify(board).isBoardPositionOccupied(target);
    }

    @Test
    public void shouldAskSourcePieceIsOpponentOfTargetPieceWhenTargetPositionIsNotEmptyOnCallingMovementIsNotAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);

        // when
        movement.isAvailableMovement();

        // then
        verify(board).getPiecePlacedOn(source);
        verify(board).getPiecePlacedOn(target);
        verify(sourcePiece).isOpponentOf(targetPiece);
    }

    @Test
    public void shouldReturnFalsyUnavailableMovementWhenSourcePieceIsReallyOpponentOfTargetPiece() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(true);

        // when
        boolean isNotAvailable = movement.isNotAvailableMovement();

        // then
        assertFalse(isNotAvailable, format("Source piece suppose not to be an available movement, but was. isNotAvailable[%s]", isNotAvailable));
    }

    @Test
    public void shouldReturnTruthyUnavailableMovementWhenSourcePieceIsNotAnOpponentOfTargetPiece() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(false);

        // when
        boolean isNotAvailable = movement.isNotAvailableMovement();

        // then
        assertTrue(isNotAvailable, format("Source piece suppose to be an unavailable movement, but wasn't. isNotAvailable[%s]", isNotAvailable));
    }

    @Test
    public void shouldAskBoardIfTargetPositionDoesExistsWhenCallingMovementIsAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.isAvailableMovement();

        // then
        verify(board, times(TWICE)).doesExists(target);
    }

    @Test
    public void shouldAskBoardIfTargetPositionIsEmptyWhenCallingMovementIsAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);

        // when
        movement.isAvailableMovement();

        // then
        verify(board).isBoardPositionEmpty(target);
    }

    @Test
    public void shouldReturnTrueWhenMovementHasTargetPositionEmptyOnCallingIsAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(true);

        // when
        boolean isEmpty = movement.isAvailableMovement();

        // then
        assertTrue(isEmpty, format("Source piece suppose to has target position empty, but wasn't. isEmpty[%s]", isEmpty));
    }

    @Test
    public void shouldAskBoardIfTargetPositionIsOccupiedWhenTargetPositionIsNotEmptyOnCallingMovementIsAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);

        // when
        movement.isAvailableMovement();

        // then
        verify(board).isBoardPositionOccupied(target);
    }

    @Test
    public void shouldAskSourcePieceIsOpponentOfTargetPieceWhenTargetPositionIsNotEmptyOnCallingMovementIsAnAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);

        // when
        movement.isAvailableMovement();

        // then
        verify(board).getPiecePlacedOn(source);
        verify(board).getPiecePlacedOn(target);
        verify(sourcePiece).isOpponentOf(targetPiece);
    }

    @Test
    public void shouldReturnTrueWhenMovementSourcePositionIsOpponnentOfTargetPositionOnCallingIsAvailableMovement() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(false);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(true);

        // when
        boolean isAvailable = movement.isAvailableMovement();

        // then
        assertTrue(isAvailable, format("Source piece suppose to is an available movement, but wasn't. isAvailable[%s]", isAvailable));
    }

    @Test
    public void shouldAskBoardIfTargetPositionDoesExistsWhenCallingMovementHasTargetPositionEmpty() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.isTargetPositionEmpty();

        // then
        verify(board).doesExists(target);
    }

    @Test
    public void shouldAskBoardIfTargetPositionIsEmptyWhenCallingMovementHasTargetPositionEmpty() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);

        // when
        movement.isTargetPositionEmpty();

        // then
        verify(board).isBoardPositionEmpty(target);
    }

    @Test
    public void shouldReturnTrueWhenMovementIsTargetPositionEmpty() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionEmpty(target)).thenReturn(true);

        // when
        boolean isEmpty = movement.isTargetPositionEmpty();

        // then
        assertTrue(isEmpty, format("Source piece suppose to has target position empty, but wasn't. isEmpty[%s]", isEmpty));
    }

    @Test
    public void shouldAskBoardIfTargetPositionDoesExistsWhenCallingMovementHasTargetPositionOccupied() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.isTargetPositionOccupied();

        // then
        verify(board).doesExists(target);
    }

    @Test
    public void shouldAskBoardIfTargetPositionIsOccupiedWhenCallingMovementHasTargetPositionOccupied() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);

        // when
        movement.isTargetPositionOccupied();

        // then
        verify(board).isBoardPositionOccupied(target);
    }

    @Test
    public void shouldReturnTrueWhenMovementIsTargetPositionOccupied() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);

        // when
        boolean isOccupied = movement.isTargetPositionOccupied();

        // then
        assertTrue(isOccupied, format("Source piece suppose to has target position occupied, but wasn't. isOccupied[%s]", isOccupied));
    }

    @Test
    public void shouldAskBoardIfTargetPositionDoesExistsWhenCallingMovementHasOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.hasOpponentOnTargetPosition();

        // then
        verify(board).doesExists(target);
    }

    @Test
    public void shouldAskBoardIfTargetPositionIsOccupiedWhenCallingMovementHasOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);

        // when
        movement.hasOpponentOnTargetPosition();

        // then
        verify(board).isBoardPositionOccupied(target);
    }

    @Test
    public void shouldAskBoardGetSourcePlacedPositionWhenCallingMovementHasOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);

        // when
        movement.hasOpponentOnTargetPosition();

        // then
        verify(board).getPiecePlacedOn(source);
    }

    @Test
    public void shouldAskBoardGetTargetPlacedPositionWhenCallingMovementHasOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);

        // when
        movement.hasOpponentOnTargetPosition();

        // then
        verify(board).getPiecePlacedOn(target);
    }

    @Test
    public void shouldAskSourcePositionIsOpponentFromTargetPositionWhenCallingMovementHasOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);

        // when
        movement.hasOpponentOnTargetPosition();

        // then
        verify(sourcePiece).isOpponentOf(targetPiece);
    }

    @Test
    public void shouldReturnTrueWhenMovementHasOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);
        when(board.isBoardPositionOccupied(target)).thenReturn(true);
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(true);

        // when
        boolean hasOpponent = movement.hasOpponentOnTargetPosition();

        // then
        assertTrue(hasOpponent, format("Source piece suppose to has opponent on target position, but wasn't. hasOpponent[%s]", hasOpponent));
    }

    @Test
    public void shouldAskBoardIfTargetPositionDoesExistsWhenCallingMovementDoesExistsTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.doesExistsTargetPosition();

        // then
        verify(board).doesExists(target);
    }

    @Test
    public void shouldReturnFalseOnAskMovementDoesExistsTargetPositionWhenTargetPositionDoesNotExistsOnBoard() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(false);

        // when
        boolean doesExists = movement.doesExistsTargetPosition();

        // then
        assertFalse(doesExists, format("Movement target position suppose not exists on board, but exists. doesExists[%s]", doesExists));
    }

    @Test
    public void shouldReturnTrueOnAskMovementDoesExistsTargetPositionWhenTargetPositionDoesExistsOnBoard() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.doesExists(target)).thenReturn(true);

        // when
        boolean doesExists = movement.doesExistsTargetPosition();

        // then
        assertTrue(doesExists, format("Movement target position suppose not exists on board, but exists. doesExists[%s]", doesExists));
    }

    @Test
    public void shouldGetPlacedPieceOnSourcePositionWhenCallingMovementThereIsAnOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);

        // when
        movement.thereIsAnOpponentOnTargetPosition();

        // then
        verify(board).getPiecePlacedOn(source);
    }

    @Test
    public void shouldGetPlacedPieceOnTargetPositionWhenCallingMovementThereIsAnOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);

        // when
        movement.thereIsAnOpponentOnTargetPosition();

        // then
        verify(board).getPiecePlacedOn(target);
    }

    @Test
    public void shouldAskSourcePieceIsOpponentOfTargetPieceWhenCallingMovementThereIsAnOpponentOnTargetPosition() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);

        // when
        movement.thereIsAnOpponentOnTargetPosition();

        // then
        verify(sourcePiece).isOpponentOf(targetPiece);
    }

    @Test
    public void shouldReturnFalseOnAskMovementIfThereIsAnOpponentPieceOnTargetPositionWhenPlacedPieceOnSourceIsNotOpponentFromPiecePlacedOnTarget() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(false);

        // when
        boolean isOpponent = movement.thereIsAnOpponentOnTargetPosition();

        // then
        assertFalse(isOpponent, format("Movement source piece suppose not to be opponent from target piece, but was. isOpponent[%s]", isOpponent));
    }

    @Test
    public void shouldReturnTrueOnAskMovementIfThereIsAnOpponentPieceOnTargetPositionWhenPlacedPieceOnSourceIsReallyOpponentFromPiecePlacedOnTarget() {
        // given
        DummyChessPiece piece = DummyChessPiece.builder().board(board).build();
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();
        when(board.getPiecePlacedOn(source)).thenReturn(sourcePiece);
        when(board.getPiecePlacedOn(target)).thenReturn(targetPiece);
        when(sourcePiece.isOpponentOf(targetPiece)).thenReturn(true);

        // when
        boolean isOpponent = movement.thereIsAnOpponentOnTargetPosition();

        // then
        assertTrue(isOpponent, format("Movement source piece suppose to be opponent from target piece, but wasn't. isOpponent[%s]", isOpponent));
    }

    @Test
    public void shouldAskBoardToRemoveMovingPieceFromSourcePositionWhenDoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.doMove();

        // then
        verify(board).removePieceFrom(source);
    }

    @Test
    public void shouldAskBoardToRemoveCapturedPieceFromTargetPositionWhenDoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.doMove();

        // then
        verify(board).removePieceFrom(target);
    }

    @Test
    public void shouldAskBoardToPlaceMovingPieceOnTargetPositionWhenDoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.doMove();

        // then
        verify(board).placePieceOn(target, sourcePiece);
    }

    @Test
    public void shouldAskMovingPieceToIncreaseMoveCountWhenDoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.doMove();

        // then
        verify(sourcePiece).increaseMoveCount();
    }

    @Test
    public void shouldDoComposedMoveWhenDoingMovementThatHasComposedMoveToDone() {
        // given
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().composedMoveDone(false).piece(piece).source(source).target(target).build();

        // when
        movement.doMove();

        // then
        assertTrue(movement.isComposedMoveDone(), format("Composed move suppose to be done, but wasn't. composedMoveDone [%s]", movement.isComposedMoveDone()));
    }

    @Test
    public void shouldComposedMoveNeverBeUndoneWhenDoingMovement() {
        // given
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().composedMoveUndone(false).piece(piece).source(source).target(target).build();

        // when
        movement.doMove();

        // then
        assertFalse(movement.isComposedMoveUndone(), format("Composed move suppose never to be undone, but was. composedMoveUndone [%s]", movement.isComposedMoveUndone()));
    }

    @Test
    public void shouldAskPlacedPieceToSetUpAvailableMovementsWhenDoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        when(board.getAllPlacedPieces()).thenReturn(List.of(sourcePiece, targetPiece));
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.doMove();

        // then
        verify(sourcePiece).setUpAvailableMovements();
        verify(targetPiece).setUpAvailableMovements();
    }

    @Test
    public void shouldReturnCapturedPieceWhenDoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(source)).thenReturn(sourcePiece);
        when(board.removePieceFrom(target)).thenReturn(targetPiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        ChessPiece capturedPiece = movement.doMove();

        // then
        assertEquals(targetPiece, capturedPiece, format("Captured piece suppose to be the movement target piece, but wasn't. capturedPiece [%s] targetPiece [%s]", capturedPiece, targetPiece));
    }

    @Test
    public void shouldAskBoardToRemoveMovingPieceFromTargetPositionWhenUndoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(targetPiece);

        // then
        verify(board).removePieceFrom(target);
    }

    @Test
    public void shouldAskBoardToPlaceBackMovingPieceOnSourcePositionWhenUndoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(targetPiece);

        // then
        verify(board).placePieceOn(source, sourcePiece);
    }

    @Test
    public void shouldAskMovingPieceToDecreaseMoveCountWhenUndoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(targetPiece);

        // then
        verify(sourcePiece).decreaseMoveCount();
    }

    @Test
    public void shouldAskBoardToPlaceBackCapturedPieceOnTargetPositionWhenUndoingMove() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(targetPiece);

        // then
        verify(board).placePieceOn(target, targetPiece);
    }

    @Test
    public void shouldNeverPlaceAnyCapturedPieceOnTargetPositionWhenCapturedPieceIsNull() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(null);

        // then
        verify(board, never()).placePieceOn(eq(target), any(ChessPiece.class));
    }

    @Test
    public void shouldComposedMoveBeUndoneWhenUndoingMovementThatHasComposedMoveToUndo() {
        // given
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().composedMoveUndone(false).piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(targetPiece);

        // then
        assertTrue(movement.isComposedMoveUndone(), format("Composed move suppose to be undone, but wasn't. composedMoveUndone [%s]", movement.isComposedMoveUndone()));
    }

    @Test
    public void shouldComposedMoveNeverBeDoneWhenUndoingMovement() {
        // given
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        DummyChessMovement movement = DummyChessMovement.builder().composedMoveDone(false).piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(targetPiece);

        // then
        assertFalse(movement.isComposedMoveDone(), format("Composed move suppose never to be done, but was. composedMoveDone [%s]", movement.isComposedMoveDone()));
    }

    @Test
    public void shouldAskAllPlacedPiecesToSetUpAvailableMovementsWhenUndoingMovement() {
        //
        when(piece.getBoard()).thenReturn(board);
        when(board.removePieceFrom(target)).thenReturn(sourcePiece);
        when(board.getAllPlacedPieces()).thenReturn(List.of(sourcePiece, targetPiece));
        DummyChessMovement movement = DummyChessMovement.builder().piece(piece).source(source).target(target).build();

        // when
        movement.undoMove(targetPiece);

        // then
        verify(sourcePiece).setUpAvailableMovements();
        verify(targetPiece).setUpAvailableMovements();
    }

    @Test
    public void shouldBuildNextNorthPositionForAGivenPositionWhenPassedMovementDirectionIsNorth() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('b', 4);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.NORTH);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

    @Test
    public void shouldBuildNextSouthPositionForAGivenPositionWhenPassedMovementDirectionIsSouth() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('b', 2);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.SOUTH);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

    @Test
    public void shouldBuildNextEastPositionForAGivenPositionWhenPassedMovementDirectionIsEast() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('c', 3);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.EAST);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

    @Test
    public void shouldBuildNextWestPositionForAGivenPositionWhenPassedMovementDirectionIsWest() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('a', 3);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.WEST);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

    @Test
    public void shouldBuildNextNortheastPositionForAGivenPositionWhenPassedMovementDirectionIsNortheast() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('c', 4);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.NORTHEAST);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

    @Test
    public void shouldBuildNextSoutheastPositionForAGivenPositionWhenPassedMovementDirectionIsSoutheast() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('c', 2);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.SOUTHEAST);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

    @Test
    public void shouldBuildNextSouthwestPositionForAGivenPositionWhenPassedMovementDirectionIsSouthwest() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('a', 2);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.SOUTHWEST);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

    @Test
    public void shouldBuildNextNorthwestPositionForAGivenPositionWhenPassedMovementDirectionIsNorthwest() {
        // given
        ChessPosition chessPosition = ChessPosition.buildPositionFor('b', 3);
        ChessPosition expectedPosition = ChessPosition.buildPositionFor('a', 4);

        // when
        ChessPosition nextPosition = ChessMovement.getNextPosition(chessPosition, MovementDirection.NORTHWEST);

        // then
        assertEquals(expectedPosition, nextPosition);
    }

}
