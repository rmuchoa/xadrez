package game;

import static chess.ChessBoard.CHESS_BOARD_SIZE;

import chess.ChessMatch;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import chess.pieces.Empty;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class UI {

    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void printMatch(ChessMatch match, List<ChessPiece> capturedPieces) {
        printMatch(match, capturedPieces, null);
    }

    public static void printMatch(ChessMatch match, List<ChessPiece> capturedPieces, List<ChessMovement> availableMovements) {
        printBoard(match, availableMovements);
        printCapturedPieces(capturedPieces);

        if (match.isNotInCheckMate()) {
            printPlay(match);
        } else {
            printCheckMate(match);
        }
    }

    private static void printPlay(ChessMatch match) {
        System.out.print("Current player: ");
        printCurrentPlayer(match);
        printMatchTurn(match);

        printCheck(match);
    }

    private static void printCheckMate(ChessMatch match) {
        System.out.println("CHECKMATE!!!");
        printWinner(match);
        printMatchTurn(match);
    }

    private static void printMatchTurn(ChessMatch match) {
        System.out.println("Turn: " + match.getTurn());
    }

    private static void printWinner(ChessMatch match) {
        System.out.print("Winner: ");
        printCurrentPlayer(match);
    }

    private static void printCurrentPlayer(ChessMatch match) {
        changeToColor(match.getCurrentPlayer());
        System.out.println(match.getCurrentPlayer());
        resetColor();
    }

    private static void printCheck(ChessMatch match) {
        if (match.isInCheck())
            System.out.println("CHECK!");
    }

    public static void printBoard(ChessMatch match, List<ChessMovement> availableMovements) {
        for (int row=0; row < match.getPieces().size(); row++)
            printBoardRow(row, match, availableMovements);

        printColumnLabels();
        System.out.println();
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String coordinate = sc.nextLine();
            char chessPositionColumn = coordinate.charAt(0);
            int chessPositionRow = Integer.parseInt(coordinate.substring(1));

            return new ChessPosition(chessPositionColumn, chessPositionRow);

        } catch (RuntimeException ex) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    // https://stackoverflow.com/questions/2979383/java-clear-the-console
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void printColumnLabels() {
        System.out.println("   a  b  c  d  e  f  g  h  ");
    }

    private static void printBoardRow(int row, ChessMatch match, List<ChessMovement> availableMovements) {
        printRowLabel(row);

        for(int column=0; column< match.getPieces().size(); column++) {
            ChessPiece piece = getPieceFrom(match, row, column);
            printPiece(piece, availableMovements);
        }

        System.out.println();
    }

    private static ChessPiece getPieceFrom(ChessMatch match, int row, int column) {
        ChessPiece piece = match.getPieces().get(row).get(column);

        if (piece == null) {
            piece = new Empty();
            piece.placeOnPosition(new ChessPosition(row, column), match.getBoard());
        }

        return piece;
    }

    private static void printRowLabel(int row) {
        System.out.print((CHESS_BOARD_SIZE - row) + "  ");
    }

    public static void printPiece(ChessPiece piece, List<ChessMovement> availableMovements) {
        if (availableMovements != null) {
            ChessPosition chessPosition = piece.getPosition();

            printPiece(piece, availableMovements.stream()
                .map(ChessMovement::getTarget)
                .anyMatch(chessPosition::equals));

        } else {
            printPiece(piece, false);
        }
    }

    public static void printPiece(ChessPiece piece, boolean background) {
        if (piece.isInCheck())
            changeToYellowBackground();

        if (piece.isInCheckMate())
            changeToRedBackground();

        if (background)
            changeToBlueBackground();

        if (piece instanceof Empty) {
            printEmptyPiece(piece);
        } else {
            printColoredPiece(piece);
        }

        System.out.print("  ");
    }

    private static void printEmptyPiece(ChessPiece piece) {
        System.out.print(piece);
        resetColor();
    }

    private static void printColoredPiece(ChessPiece piece) {
        changeToColor(piece.getColor());
        System.out.print(piece);
        resetColor();
    }

    public static void printCapturedPieces(List<ChessPiece> capturedPieces) {
        System.out.println("Captured pieces:");
        printWhiteCapturedPieces(capturedPieces);
        printBlackCapturedPieces(capturedPieces);
        System.out.println();
    }

    private static void printWhiteCapturedPieces(List<ChessPiece> capturedPieces) {
        List<ChessPiece> whiteCaptured = capturedPieces.stream()
            .filter(ChessPiece::isWhitePiece)
            .toList();

        changeToWhiteColor();
        System.out.print("White: ");
        printArray(whiteCaptured.toArray());
        resetColor();
    }

    private static void printBlackCapturedPieces(List<ChessPiece> capturedPieces) {
        List<ChessPiece> blackCaptured = capturedPieces.stream()
            .filter(ChessPiece::isBlackPiece)
            .toList();

        changeToBlackColor();
        System.out.print("Black: ");
        printArray(blackCaptured.toArray());
        resetColor();
    }

    private static void printArray(Object[] capturedPieces) {
        System.out.println(Arrays.toString(capturedPieces));
    }

    private static void changeToYellowBackground() {
        System.out.print(ANSI_YELLOW_BACKGROUND);
    }

    private static void changeToRedBackground() {
        System.out.print(ANSI_RED_BACKGROUND);
    }

    private static void changeToBlueBackground() {
        System.out.print(ANSI_BLUE_BACKGROUND);
    }

    private static void changeToColor(Color color) {
        if (Color.WHITE.equals(color))
            changeToWhiteColor();
        else
            changeToBlackColor();
    }

    private static void changeToWhiteColor() {
        System.out.print(ANSI_WHITE);
    }

    private static void changeToBlackColor() {
        System.out.print(ANSI_GREEN);
    }

    private static void resetColor() {
        System.out.print(ANSI_RESET);
    }

}
