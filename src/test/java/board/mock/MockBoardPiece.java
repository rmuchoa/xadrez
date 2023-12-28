package board.mock;

import board.BoardPiece;
import java.util.Collections;
import java.util.List;

public class MockBoardPiece extends BoardPiece<MockBoardPosition, MockBoardPiece, MockBoard> {

    private MockBoardPosition availableTargetPosition;

    protected MockBoardPiece(MockBoard board) {
        super(board);
    }

    public MockBoardPiece(MockBoard board, MockBoardPosition position, MockBoardPosition availableTargetPosition) {
        super(board);
        placeOnPosition(position);
        this.availableTargetPosition = availableTargetPosition;
    }

    @Override
    public List<MockBoardPosition> getAllAvailableTargetPositions() {
        if (availableTargetPosition != null) {
            return Collections.singletonList(availableTargetPosition);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean equals(Object any) {
        return any instanceof MockBoardPiece
            && super.equals(any);
    }

    public static MockBoardPieceBuilder builder() {
        return MockBoardPieceBuilder.builder();
    }

    public static class MockBoardPieceBuilder {

        private MockBoard board;
        private MockBoardPosition position;
        private MockBoardPosition availableTargetPosition;

        private MockBoardPieceBuilder() {}

        public static MockBoardPieceBuilder builder() {
            return new MockBoardPieceBuilder();
        }

        public MockBoardPieceBuilder position(MockBoardPosition position) {
            this.position = position;
            return this;
        }

        public MockBoardPieceBuilder board(MockBoard board) {
            this.board = board;
            return this;
        }

        public MockBoardPieceBuilder availableTargetPosition(MockBoardPosition position) {
            this.availableTargetPosition = position;
            return this;
        }

        public MockBoardPiece build() {
            return new MockBoardPiece(board, position, availableTargetPosition);
        }

        public StubMockBoardPiece stub() {
            return new StubMockBoardPiece(board, position);
        }
    }

    public static class StubMockBoardPiece extends MockBoardPiece {

        private final MockBoardPosition position;

        protected StubMockBoardPiece(MockBoard board, MockBoardPosition position) {
            super(board);
            this.position = position;
        }

        @Override
        public MockBoardPosition getPosition() {
            return position;
        }

        @Override
        public boolean equals(Object any) {
            return any instanceof StubMockBoardPiece
                && position.equals(((StubMockBoardPiece) any).getPosition());
        }

        @Override
        public String toString() {
            return "StubMockBoardPiece["+position.toString()+"]";
        }
    }

}
