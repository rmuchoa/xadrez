package application;

import static chess.ChessMatch.CHESS_BOARD_SIZE;

import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import chess.pieces.NoPlaced;
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

    public static void printBoard(ChessPiece[][] pieces, List<ChessPosition> possibleMovements) {
        for (int row=0; row < pieces.length; row++)
            printBoardRow(row, pieces, possibleMovements);

        printColumnLabels();
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

    private static void printBoardRow(int row, ChessPiece[][] pieces, List<ChessPosition> possibleMovements) {
        printRowLabel(row);

        for(int column=0; column< pieces.length; column++) {
            ChessPiece piece = getPieceFrom(pieces, row, column);
            printPiece(piece, possibleMovements);
        }

        System.out.println();
    }

    private static ChessPiece getPieceFrom(ChessPiece[][] pieces, int row, int column) {
        ChessPiece piece = pieces[row][column];

        if (piece == null) {
            piece = NoPlaced.builder().build();
            piece.placeOnPosition(ChessPosition.builder().boardPostion(row, column).build());
        }

        return piece;
    }

    private static void printRowLabel(int row) {
        System.out.print((CHESS_BOARD_SIZE - row) + "  ");
    }

    public static void printPiece(ChessPiece piece, List<ChessPosition> possibleMovements) {
        if (possibleMovements != null) {
            ChessPosition chessPosition = piece.getPosition();
            printPiece(piece, possibleMovements.stream().anyMatch(chessPosition::equals));
        } else {
            printPiece(piece, false);
        }
    }

    public static void printPiece(ChessPiece piece, boolean background) {
        if (background)
            System.out.print(ANSI_BLUE_BACKGROUND);

        if (piece instanceof NoPlaced)
            System.out.print(piece+ANSI_RESET);
        else
            printColoredPiece(piece);

        System.out.print("  ");
    }

    private static void printColoredPiece(ChessPiece piece) {
        if (piece.getColor() == Color.WHITE)
            System.out.print(ANSI_WHITE + piece + ANSI_RESET);
        else
            System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
    }

}
