package chess;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static java.lang.String.format;

import chess.dummy.DummyChessBoard;
import chess.pieces.King;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class ChessBoardTest {

    @Mock
    private ChessMatch match;

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldGetMovementForThrowChessExceptionWhenBoardSourcePositionIsEmpty() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, null);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.getMovementFor(source, target);
        });

        // then
        assertEquals(format("There is no piece present on source position %s", source), thrown.getMessage());
    }

    @Test
    public void shouldGetMovementForThrowChessExceptionWhenSourcePieceIsFromOpponentPlayer() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.getMovementFor(source, target);
        });

        // then
        assertEquals(format("The chosen piece %s on %s is not yours", piece, source), thrown.getMessage());
    }

    @Test
    public void shouldGetMovementForThrowChessExceptionWhenSourcePieceHasNoAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.hasNoAvailableMovements()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.getMovementFor(source, target);
        });

        // then
        assertEquals(format("There is no possible movements for the chosen %s piece", piece), thrown.getMessage());
    }

    @Test
    public void shouldGetMovementForThrowChessExceptionWhenFoundPieceCanNotTargetGivenTargetPosition() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.canNotTargetThis(eq(target))).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.getMovementFor(source, target);
        });

        // then
        assertEquals(format("The piece cannot move to target position %s", target), thrown.getMessage());
    }

    @Test
    public void shouldGetMovementForDoesNotThrowAnyExceptionWhenPieceIsFromOpponentPlayerAndHasSomeAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(false);
        when(piece.hasNoAvailableMovements()).thenReturn(false);
        when(piece.canNotTargetThis(eq(target))).thenReturn(false);

        // when
        assertDoesNotThrow(() -> {
            board.getMovementFor(source, target);
        });
    }

    @Test
    public void shouldAskFoundSourcePieceGetMovementForSourcePositionWhenBoardGetMovementFromSourceToTargetPositions() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(false);
        when(piece.hasNoAvailableMovements()).thenReturn(false);
        when(piece.canNotTargetThis(eq(target))).thenReturn(false);

        // when
        board.getMovementFor(source, target);

        // then
        verify(piece).getMovementFor(eq(target));
    }

    @Test
    public void shouldReturnGotMovementWhenFoundSourcePieceGetValidMovementForSourceAndTargetPositions() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        ChessMovement movement = mock(ChessMovement.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(false);
        when(piece.hasNoAvailableMovements()).thenReturn(false);
        when(piece.canNotTargetThis(eq(target))).thenReturn(false);
        when(piece.getMovementFor(eq(target))).thenReturn(movement);

        // when
        ChessMovement resultantMovement = board.getMovementFor(source, target);

        // then
        assertEquals(movement, resultantMovement, format("Resultant movement between source %s and target %s positions suppose to be %s, but wasn't, was %s.", source, target, movement, resultantMovement));
    }

    @Test
    public void shouldPiecePresenceValidationDoesNotThrowAnyExceptionWhenBoardPositionIsNotEmpty() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        assertDoesNotThrow(() -> {
            board.validatePiecePresenceOn(source);
        });
    }

    @Test
    public void shouldPiecePresenceValidationThrowChessExceptionWhenBoardPositionIsEmpty() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, null);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validatePiecePresenceOn(source);
        });

        // then
        assertEquals(format("There is no piece present on source position %s", source), thrown.getMessage());
    }

    @Test
    public void shouldAskPieceIsFromOpponentPlayerWhenValidatingCurrentPlayerPiecePosition() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        board.validateCurrentPlayerPiece(source);

        // then
        verify(piece).isFromOpponentPlayer();
    }

    @Test
    public void shouldBoardCurrentPlayerPieceValidationDoesNotThrowAnyExceptionWhenPieceIsFromOpponentPlayer() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(false);

        // when
        assertDoesNotThrow(() -> {
            board.validateCurrentPlayerPiece(source);
        });
    }

    @Test
    public void shouldBoardCurrentPlayerPieceValidationThrowChessExceptionWhenPieceIsFromOpponentPlayer() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateCurrentPlayerPiece(source);
        });

        // then
        assertEquals(format("The chosen piece %s on %s is not yours", piece, source), thrown.getMessage());
    }

    @Test
    public void shouldAskPieceHasNoAvailableMovementsWhenValidatingPieceMobilityForPosition() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        board.validatePieceMobility(source);

        // then
        verify(piece).hasNoAvailableMovements();
    }

    @Test
    public void shouldBoardPieceMobilityValidationDoesNotThrowAnyExceptionWhenPieceHasSomeAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.hasNoAvailableMovements()).thenReturn(false);

        // when
        assertDoesNotThrow(() -> {
            board.validatePieceMobility(source);
        });
    }

    @Test
    public void shouldBoardPieceMobilityValidationThrowChessExceptionWhenPieceHasNoAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.hasNoAvailableMovements()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validatePieceMobility(source);
        });

        // then
        assertEquals(format("There is no possible movements for the chosen %s piece", piece), thrown.getMessage());
    }

    @Test
    public void shouldOriginMobilityValidationThrowChessExceptionWhenBoardPositionIsEmpty() {
        // given
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, null);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateOriginMobilityFrom(source);
        });

        // then
        assertEquals(format("There is no piece present on source position %s", source), thrown.getMessage());
    }

    @Test
    public void shouldOriginMobilityValidationThrowChessExceptionWhenPieceIsFromOpponentPlayer() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateOriginMobilityFrom(source);
        });

        // then
        assertEquals(format("The chosen piece %s on %s is not yours", piece, source), thrown.getMessage());
    }

    @Test
    public void shouldOriginMobilityValidationThrowChessExceptionWhenPieceHasNoAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.hasNoAvailableMovements()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateOriginMobilityFrom(source);
        });

        // then
        assertEquals(format("There is no possible movements for the chosen %s piece", piece), thrown.getMessage());
    }

    @Test
    public void shouldOriginMobilityValidationDoesNotThrowAnyExceptionWhenPieceIsFromOpponentPlayerAndHasSomeAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(false);
        when(piece.hasNoAvailableMovements()).thenReturn(false);

        // when
        assertDoesNotThrow(() -> {
            board.validateOriginMobilityFrom(source);
        });
    }

    @Test
    public void shouldMobilityValidationThrowChessExceptionWhenBoardPositionIsEmpty() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, null);

        when(piece.getPosition()).thenReturn(source);
        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateMobilityFor(piece);
        });

        // then
        assertEquals(format("There is no piece present on source position %s", source), thrown.getMessage());
    }

    @Test
    public void shouldMobilityValidationThrowChessExceptionWhenPieceIsFromOpponentPlayer() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(piece.getPosition()).thenReturn(source);
        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateMobilityFor(piece);
        });

        // then
        assertEquals(format("The chosen piece %s on %s is not yours", piece, source), thrown.getMessage());
    }

    @Test
    public void shouldMobilityValidationThrowChessExceptionWhenPieceHasNoAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(piece.getPosition()).thenReturn(source);
        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.hasNoAvailableMovements()).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateMobilityFor(piece);
        });

        // then
        assertEquals(format("There is no possible movements for the chosen %s piece", piece), thrown.getMessage());
    }

    @Test
    public void shouldMobilityValidationDoesNotThrowAnyExceptionWhenPieceIsFromOpponentPlayerAndHasSomeAvailableMovement() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(piece.getPosition()).thenReturn(source);
        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.isFromOpponentPlayer()).thenReturn(false);
        when(piece.hasNoAvailableMovements()).thenReturn(false);

        // when
        assertDoesNotThrow(() -> {
            board.validateMobilityFor(piece);
        });
    }

    @Test
    public void shouldAskPieceCanNotTargetThisGivenTargetPieceWhenValidatingTargetPositionAvailability() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);

        // when
        board.validateTargetPositionAvailability(source, target);

        // then
        verify(piece).canNotTargetThis(eq(target));
    }

    @Test
    public void shouldThrowChessExceptionWhenFoundPieceCanNotTargetGivenTargetPosition() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.canNotTargetThis(eq(target))).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, () -> {
            board.validateTargetPositionAvailability(source, target);
        });

        // then
        assertEquals(format("The piece cannot move to target position %s", target), thrown.getMessage());
    }

    @Test
    public void shouldThrowChessExceptionWhenFoundPieceCAskPieceCanNotTargetThisGivenTargetPieceWhenValidatingTargetPositionAvailability() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPosition source = mock(ChessPosition.class);
        ChessPosition target = mock(ChessPosition.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(1, piece);

        when(source.getMatrixRow()).thenReturn(0);
        when(source.getMatrixColumn()).thenReturn(1);
        when(piece.canNotTargetThis(eq(target))).thenReturn(false);

        // when
        assertDoesNotThrow(() -> {
            board.validateTargetPositionAvailability(source, target);
        });
    }

    @Test
    public void shouldAskKingRevokeCheckWhenCurrentPlayerKingCannotBeTargetedByAnyOpponentPiece() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, king);
        board.getAllPieces().get(1).set(3, piece);

        when(king.isKing()).thenReturn(true);
        when(king.isFromCurrentPlayer()).thenReturn(true);
        when(king.isOpponentOf(piece)).thenReturn(true);
        when(king.canBeTargetedBy(piece)).thenReturn(false);

        // when
        board.checkOwnKing();

        // then
        verify(king).revokeCheck();
    }

    @Test
    public void shouldThrowChessExceptionWhenCurrentPlayerKingCanBeTargetedByOpponentPiece() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, king);
        board.getAllPieces().get(1).set(3, piece);

        when(king.isKing()).thenReturn(true);
        when(king.isFromCurrentPlayer()).thenReturn(true);
        when(king.isOpponentOf(piece)).thenReturn(true);
        when(king.canBeTargetedBy(piece)).thenReturn(true);

        // when
        ChessException thrown = assertThrows(ChessException.class, board::checkOwnKing);

        // then
        assertEquals("This movement put your own king in check. Undoing all movement!", thrown.getMessage());
        verify(king, never()).revokeCheck();
    }

    @Test
    public void shouldOpponentKingIsNotInCheckMateWhenCantBeTargetedByAnyEnemyPlacedPiece() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, king);
        board.getAllPieces().get(1).set(3, piece);

        when(king.isKing()).thenReturn(true);
        when(king.isFromOpponentPlayer()).thenReturn(true);
        when(king.isOpponentOf(piece)).thenReturn(true);
        when(king.canBeTargetedBy(piece)).thenReturn(false);

        // when
        boolean inCheckMate = board.isOpponentKingInCheckMate();

        // then
        assertFalse(inCheckMate, format("King inCheckMate suppose to be false, but wasn't. inCheckMate [%s]", inCheckMate));
    }

    @Test
    public void shouldOpponentKingIsNotInCheckMateWhenCanBeTargetedBySomeEnemyPlacedPieceAndHasNoCompanionCheckSalvation() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        ChessPiece companion = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, king);
        board.getAllPieces().get(1).set(3, piece);
        board.getAllPieces().get(2).set(4, companion);

        when(king.isKing()).thenReturn(true);
        when(king.isFromOpponentPlayer()).thenReturn(true);
        when(king.isOpponentOf(piece)).thenReturn(true);
        when(king.canBeTargetedBy(piece)).thenReturn(true);
        when(king.isCompanionOf(companion)).thenReturn(true);
        when(king.canBeSavedDuringCheckBy(companion)).thenReturn(true);

        // when
        boolean inCheckMate = board.isOpponentKingInCheckMate();

        // then
        assertFalse(inCheckMate, format("King inCheckMate suppose to be false, but wasn't. inCheckMate [%s]", inCheckMate));
        verify(king, never()).informCheckMate();
        verify(king).informCheck();
    }

    @Test
    public void shouldOpponentKingIsInCheckMateWhenCanBeTargetedBySomeEnemyPlacedPieceButHasNoCompanionCheckSalvation() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        ChessPiece companion = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, king);
        board.getAllPieces().get(1).set(3, piece);
        board.getAllPieces().get(2).set(4, companion);

        when(king.isKing()).thenReturn(true);
        when(king.isFromOpponentPlayer()).thenReturn(true);
        when(king.isOpponentOf(piece)).thenReturn(true);
        when(king.canBeTargetedBy(piece)).thenReturn(true);
        when(king.isCompanionOf(companion)).thenReturn(true);
        when(king.canBeSavedDuringCheckBy(companion)).thenReturn(false);

        // when
        boolean inCheckMate = board.isOpponentKingInCheckMate();

        // then
        assertTrue(inCheckMate, format("King inCheckMate suppose to be true, but wasn't. inCheckMate [%s]", inCheckMate));
        verify(king).informCheckMate();
    }

    @Test
    public void shouldAskGivenKingIsCompanionOfPlacedPieceWhenCallingHasNoCheckSalvation() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, piece);

        // when
        board.hasNoCheckSalvation(king);

        // then
        verify(king).isCompanionOf(piece);
    }

    @Test
    public void shouldAskGivenKingCanBeSavedDuringCheckByPlacedPieceWhenCallingHasNoCheckSalvationAndIsCompanionOf() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, piece);
        when(king.isCompanionOf(piece)).thenReturn(true);

        // when
        board.hasNoCheckSalvation(king);

        // then
        verify(king).canBeSavedDuringCheckBy(piece);
    }

    @Test
    public void shouldReturnTrueWhenKingIsNotACompanionOfAnyPlacedPieceWhenCallingHasNoCheckSalvation() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, piece);
        when(king.isCompanionOf(piece)).thenReturn(false);

        // when
        boolean hasNoSalvation = board.hasNoCheckSalvation(king);

        // then
        assertTrue(hasNoSalvation, format("King hasNoSalvation suppose to be true, but wasn't. hasNoSalvation[%s]", hasNoSalvation));
    }

    @Test
    public void shouldReturnTrueWhenKingIsCompanionOfPlacedPieceButCantBeSavedByItWhenCallingHasNoCheckSalvation() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, piece);
        when(king.isCompanionOf(piece)).thenReturn(true);
        when(king.canBeSavedDuringCheckBy(piece)).thenReturn(false);

        // when
        boolean hasNoSalvation = board.hasNoCheckSalvation(king);

        // then
        assertTrue(hasNoSalvation, format("King hasNoSalvation suppose to be true, but wasn't. hasNoSalvation[%s]", hasNoSalvation));
    }

    @Test
    public void shouldReturnFalseWhenKingIsCompanionOfPlacedPieceAndCanBeSavedByItWhenCallingHasNoCheckSalvation() {
        // given
        King king = mock(King.class);
        ChessPiece piece = mock(ChessPiece.class);
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, piece);
        when(king.isCompanionOf(piece)).thenReturn(true);
        when(king.canBeSavedDuringCheckBy(piece)).thenReturn(true);

        // when
        boolean hasNoSalvation = board.hasNoCheckSalvation(king);

        // then
        assertFalse(hasNoSalvation, format("King hasNoSalvation suppose to be false, but wasn't. hasNoSalvation[%s]", hasNoSalvation));
    }

    @Test
    public void shouldAskEveryPlacedPieceToBeClonedIntoClonedBoard() {
        // given
        ChessPiece piece = mock(ChessPiece.class);
        ChessPiece clonedPiece = mock(ChessPiece.class);
        ChessBoard clonedBoard = mock(ChessBoard.class);
        List<List<ChessPiece>> allPieces = buildEmptyBoard();
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(0).set(2, piece);
        when(piece.clonePiece(clonedBoard)).thenReturn(clonedPiece);
        when(clonedBoard.getAllPieces()).thenReturn(allPieces);

        // when
        board.cloneIntoBoard(clonedBoard);

        // then
        verify(piece).clonePiece(clonedBoard);
    }

    @Test
    public void shouldClonedPieceBePlacedExactlyTheSamePositionOnClonedBoard() {
        // given
        int linePosition = 1;
        int columnPosition = 0;
        ChessPiece piece = mock(ChessPiece.class);
        ChessPiece clonedPiece = mock(ChessPiece.class);
        ChessBoard clonedBoard = mock(ChessBoard.class);
        List<List<ChessPiece>> allPieces = buildEmptyBoard();
        DummyChessBoard board = DummyChessBoard.builder().match(match).build();
        board.getAllPieces().get(linePosition).set(columnPosition, piece);
        when(piece.clonePiece(clonedBoard)).thenReturn(clonedPiece);
        when(clonedBoard.getAllPieces()).thenReturn(allPieces);

        // when
        board.cloneIntoBoard(clonedBoard);

        // then
        assertEquals(allPieces.get(linePosition).get(columnPosition), clonedPiece);
    }

    private List<List<ChessPiece>> buildEmptyBoard() {

        ChessPiece[][] emptyBoard = new ChessPiece[][] {
            new ChessPiece[] { null, null, null, null, null, null, null, null },
            new ChessPiece[] { null, null, null, null, null, null, null, null },
            new ChessPiece[] { null, null, null, null, null, null, null, null },
            new ChessPiece[] { null, null, null, null, null, null, null, null },
            new ChessPiece[] { null, null, null, null, null, null, null, null },
            new ChessPiece[] { null, null, null, null, null, null, null, null },
            new ChessPiece[] { null, null, null, null, null, null, null, null },
            new ChessPiece[] { null, null, null, null, null, null, null, null }
        };

        return Arrays.stream(emptyBoard)
            .map(line -> Arrays.stream(line).collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

}
