package chess.pieces;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.openMocks;

import chess.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KingTest {

    @BeforeEach
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void shouldReturnTrueOnAskKingIsInCheckWhenKingIsFlaggedAsInCheckAndNotInCheckMate() {
        // given
        King king = new King(Color.WHITE);
        king.inCheck = true;
        king.inCheckMate = false;

        // when
        boolean inCheck = king.isInCheck();

        // then
        assertTrue(inCheck, format("King isInCheck suppose to be true, but wasn't, was %s. inCheck[%s] and inCheckMate[%s].", inCheck, king.inCheck, king.inCheckMate));
    }

    @Test
    public void shouldReturnFalseOnAskKingIsInCheckWhenKingIsFlaggedAsFalseInCheck() {
        // given
        King king = new King(Color.WHITE);
        king.inCheck = false;
        king.inCheckMate = false;

        // when
        boolean inCheck = king.isInCheck();

        // then
        assertFalse(inCheck, format("King isInCheck suppose to be false, but wasn't, was %s. inCheck[%s] and inCheckMate[%s].", inCheck, king.inCheck, king.inCheckMate));
    }

    @Test
    public void shouldReturnFalseOnAskKingIsInCheckWhenKingIsFlaggedAsTrueInCheckButTrueInCheckMateToo() {
        // given
        King king = new King(Color.WHITE);
        king.inCheck = true;
        king.inCheckMate = true;

        // when
        boolean inCheck = king.isInCheck();

        // then
        assertFalse(inCheck, format("King isInCheck suppose to be false, but wasn't, was %s. inCheck[%s] and inCheckMate[%s].", inCheck, king.inCheck, king.inCheckMate));
    }

    @Test
    public void shouldKingApplyTrueOnInCheckFlagWhenAskKingToInformCheck() {
        // given
        King king = new King(Color.WHITE);

        // when
        king.informCheck();

        // then
        assertTrue(king.inCheck, format("King suppose to is in check, but wasn't. inCheck[%s].", king.inCheck));
    }

    @Test
    public void shouldKingApplyFalseOnInCheckFlagWhenAskKingToRevokeCheck() {
        // given
        King king = new King(Color.WHITE);
        king.inCheck = true;

        // when
        king.revokeCheck();

        // then
        assertFalse(king.inCheck, format("King suppose to is not in check, but was. inCheck[%s].", king.inCheck));
    }

    @Test
    public void shouldKingApplyTrueOnInCheckMateFlagWhenAskKingToInformCheckMate() {
        // given
        King king = new King(Color.WHITE);

        // when
        king.informCheckMate();

        // then
        assertTrue(king.inCheckMate, format("King suppose to is in check-mate, but wasn't. inCheckMate[%s].", king.inCheckMate));
    }

}
