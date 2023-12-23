package application;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();

        while (true) {
            try {
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces());
                System.out.println();
                System.out.print("Source: ");
                ChessPosition source = UI.readChessPosition(scanner);

                System.out.print("Target: ");
                ChessPosition target = UI.readChessPosition(scanner);

                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);
                System.out.println();
                System.out.println("Captured: ["+capturedPiece+"]");

            } catch (GameException ex) {
                System.out.println(ex.getMessage());
                scanner.nextLine();
                throw ex;
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
                scanner.nextLine();
                throw ex;
            }
        }

    }
}