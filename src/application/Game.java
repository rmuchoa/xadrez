package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> capturedPieces = new ArrayList<>();

        while (true) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, capturedPieces, null);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition sourcePosition = UI.readChessPosition(scanner);

                List<ChessPosition> possibleMovements = chessMatch.getAllAvailableTargetFor(sourcePosition);
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMovements);
                System.out.println();

                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(sourcePosition, target);
                if (capturedPiece != null) {
                    capturedPieces.add(capturedPiece);
                }

            } catch (GameException ex) {
                System.out.println(ex.getMessage());
                scanner.nextLine();
                //throw ex;
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
                scanner.nextLine();
                //throw ex;
            }
        }

    }
}