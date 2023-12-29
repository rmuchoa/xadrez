package board.mock;

import static org.junit.jupiter.api.Assertions.*;

import board.Board;
import board.mock.MockBoardPosition.StubMockBoardPosition;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MockBoard extends Board<MockBoardPosition, MockBoardPiece, MockBoard> {

    public static final int TOTAL_ROWS = 8;
    public static final int TOTAL_COLUMNS = 8;

    protected MockBoard(int totalRows, int totalColumns) {
        super(totalRows, totalColumns);
    }

    public static MockBoardBuilder builderMock() {
        return MockBoardBuilder.builder();
    }

    public static class MockBoardBuilder {

        private int totalRows;
        private int totalColumns;
        private MockBoardPiece placedPiece;
        private MockBoardPosition placedPosition;
        private MockBoardPiece stubbedPlacedPiece;
        private MockBoardPosition stubbedPlacedPosition;
        private MockBoardPosition stubbedExistingPosition;
        private Boolean stubbedExistence;
        private StubMockBoardPosition stubbedNotExistingPosition;
        private Boolean stubbedNotExistence;

        private MockBoardBuilder() {}

        public static MockBoardBuilder builder() {
            return new MockBoardBuilder();
        }

        public MockBoardBuilder filled() {
            return totalRows().totalColumns();
        }

        public MockBoardBuilder withPlacedPiece(MockBoardPiece piece, MockBoardPosition position) {
            this.placedPosition = position;
            this.placedPiece = piece;
            return this;
        }

        public MockBoardBuilder totalRows() {
            return totalRows(TOTAL_ROWS);
        }

        public MockBoardBuilder totalRows(int totalRows) {
            this.totalRows = totalRows;
            return this;
        }

        public MockBoardBuilder totalColumns() {
            return totalColumns(TOTAL_COLUMNS);
        }

        public MockBoardBuilder totalColumns(int totalColumns) {
            this.totalColumns = totalColumns;
            return this;
        }

        public MockBoardBuilder stubGetPiecePlacedOn(MockBoardPosition givenPosition, MockBoardPiece givenPiece) {
            this.stubbedPlacedPosition = givenPosition;
            this.stubbedPlacedPiece = givenPiece;
            return this;
        }

        public MockBoardBuilder stubDoesExists(MockBoardPosition givenPosition, Boolean givenExistence) {
            this.stubbedExistingPosition = givenPosition;
            this.stubbedExistence = givenExistence;
            return this;
        }

        public MockBoardBuilder stubDoesNotExists(StubMockBoardPosition givenPosition, Boolean givenNotExistence) {
            this.stubbedNotExistingPosition = givenPosition;
            this.stubbedNotExistence = givenNotExistence;
            return this;
        }

        public MockBoard build() {
            MockBoard mockBoard = new MockBoard(totalRows, totalColumns);

            if (placedPosition != null && placedPiece != null)
                mockBoard.placePieceOn(placedPosition, placedPiece);

            return mockBoard;
        }

        public StubMockBoard stub() {
            return new StubMockBoard(totalRows, totalColumns, stubbedPlacedPiece, stubbedPlacedPosition,
                stubbedExistingPosition, stubbedExistence, stubbedNotExistingPosition, stubbedNotExistence);
        }
    }

    public static class StubMockBoard extends MockBoard {

        public static final int JUST_ONE_TIME = 1;
        private Map<CallId, Integer> callCounters;
        private final MockBoardPiece placedPiece;
        private final MockBoardPosition placedPosition;
        private final MockBoardPosition existingPosition;
        private final Boolean existence;
        private final StubMockBoardPosition notExistingPosition;
        private final Boolean notExistence;

        protected StubMockBoard(int totalRows, int totalColumns, MockBoardPiece placedPiece, MockBoardPosition placedPosition,
            MockBoardPosition existingPosition, Boolean existence, StubMockBoardPosition notExistingPosition,
            Boolean notExistence) {
            super(totalRows, totalColumns);
            this.placedPiece = placedPiece;
            this.placedPosition = placedPosition;
            this.existingPosition = existingPosition;
            this.existence = existence;
            this.notExistingPosition = notExistingPosition;
            this.notExistence = notExistence;
            this.callCounters = new HashMap<>();
        }

        public void verifyThatMethodGetPiecePlacedOnWasCalledAtLeastOnce(MockBoardPosition position) {
            verifyCalled("getPiecePlacedOn", position.toString());
        }

        public void verifyThatMethodDoesExistsWasCalledAtLeastOnce(MockBoardPosition position) {
            verifyCalled("doesExists", position.toString());
        }

        public void verifyThatMethodDoesNotExistsWasCalledAtLeastOnce(MockBoardPosition position) {
            verifyCalled("doesNotExists", position.toString());
        }

        @Override
        public MockBoardPiece getPiecePlacedOn(MockBoardPosition position) {
            incrementCallCounterWithPosition("getPiecePlacedOn", position);
            if (placedPosition.equals(position)) {
                return placedPiece;
            }
            return null;
        }

        @Override
        public boolean doesExists(MockBoardPosition position) {
            incrementCallCounterWithPosition("doesExists", position);
            if (existingPosition.equals(position)) {
                return existence;
            }
            return false;
        }

        @Override
        public boolean doesNotExists(MockBoardPosition position) {
            incrementCallCounterWithPosition("doesNotExists", position);
            if (notExistingPosition.equals(position)) {
                return notExistence;
            }
            return false;
        }

        public void incrementCallCounterWithPosition(String method, MockBoardPosition position) {
            if (position != null)
                incrementCallCounter(method, position.toString());
            else
                incrementCallCounter(method, null);
        }

        public void incrementCallCounter(String method, String argument) {
            CallId id = CallId.of(method, argument);
            if (callCounters.containsKey(id)) {
                Integer times = callCounters.get(id);
                callCounters.put(id, ++times);
            } else {
                callCounters.put(id, JUST_ONE_TIME);
            }
        }

        public void verifyCalled(String method, String argument) {
            verifyCalled(method, argument, true, JUST_ONE_TIME, 0);
        }

        public void verifyCalled(String method, String argument, Boolean atLeast, Integer min, Integer expected) {
            CallId id = CallId.of(method, argument);
            if (callCounters.containsKey(id)) {
                Integer callTimes = callCounters.get(id);
                assertTrue(times(callTimes, atLeast, min, expected), String.format("The method was called in an unespected condition. %s times.", callTimes));
            } else {
                fail(String.format("The expected method %s(%s) was never called!", method, argument));
            }
        }

        private static boolean times(int times, boolean atLeast, int min, int expected) {
            return atLeast ? atLeast(min, times) : exaclty(expected, times);
        }

        private static boolean exaclty(int expected, int times) {
            return expected == times;
        }

        private static boolean atLeast(int min, int expected) {
            return expected >= min;
        }

        public static class CallId {

            private String value;

            public CallId(String method, String argument) {
                this.value = String.format("%s(%s)", method, argument);
            }

            public static CallId of(String method, String argument) {
                return new CallId(method, argument);
            }

            @Override
            public boolean equals(Object any) {
                return any instanceof CallId
                    && Objects.equals(value, ((CallId) any).value);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value);
            }

            @Override
            public String toString() {
                return value;
            }
        }

    }

}
