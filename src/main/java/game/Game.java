package game;

import chess.ChessMatch;
import chess.ChessMovement;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner scanner;
    private final ChessMatch chessMatch;
    private final List<ChessPiece> capturedPieces;

    public Game() {
        scanner = new Scanner(System.in);
        chessMatch = new ChessMatch();
        capturedPieces = new ArrayList<>();
    }

    public void startMatch() {

        while (chessMatch.isNotInCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(chessMatch, capturedPieces);
                System.out.println();
                System.out.print("Source: ");
                ChessPosition sourcePosition = UI.readChessPosition(scanner);

                List<ChessMovement> availableMovements = chessMatch.getAvailableMovementsFor(sourcePosition);
                UI.clearScreen();
                UI.printBoard(chessMatch, availableMovements);
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

        UI.clearScreen();
        UI.printMatch(chessMatch, capturedPieces);

    }

    private void initialSetup() {
        chessMatch.placeNewPiece('a', 1, new Rook   (Color.WHITE));
        chessMatch.placeNewPiece('b', 1, new Knight (Color.WHITE));
        chessMatch.placeNewPiece('c', 1, new Bishop (Color.WHITE));
        chessMatch.placeNewPiece('d', 1, new Queen  (Color.WHITE));
        chessMatch.placeNewPiece('e', 1, new King   (Color.WHITE));
        chessMatch.placeNewPiece('f', 1, new Bishop (Color.WHITE));
        chessMatch.placeNewPiece('g', 1, new Knight (Color.WHITE));
        chessMatch.placeNewPiece('h', 1, new Rook   (Color.WHITE));

        chessMatch.placeNewPiece('a', 2, new Pawn   (Color.WHITE));
        chessMatch.placeNewPiece('b', 2, new Pawn   (Color.WHITE));
        chessMatch.placeNewPiece('c', 2, new Pawn   (Color.WHITE));
        chessMatch.placeNewPiece('d', 2, new Pawn   (Color.WHITE));
        chessMatch.placeNewPiece('e', 2, new Pawn   (Color.WHITE));
        chessMatch.placeNewPiece('f', 2, new Pawn   (Color.WHITE));
        chessMatch.placeNewPiece('g', 2, new Pawn   (Color.WHITE));
        chessMatch.placeNewPiece('h', 2, new Pawn   (Color.WHITE));

        chessMatch.placeNewPiece('a', 8, new Rook   (Color.BLACK));
        chessMatch.placeNewPiece('b', 8, new Knight (Color.BLACK));
        chessMatch.placeNewPiece('c', 8, new Bishop (Color.BLACK));
        chessMatch.placeNewPiece('d', 8, new Queen  (Color.BLACK));
        chessMatch.placeNewPiece('e', 8, new King   (Color.BLACK));
        chessMatch.placeNewPiece('f', 8, new Bishop (Color.BLACK));
        chessMatch.placeNewPiece('g', 8, new Knight (Color.BLACK));
        chessMatch.placeNewPiece('h', 8, new Rook   (Color.BLACK));

        chessMatch.placeNewPiece('a', 7, new Pawn   (Color.BLACK));
        chessMatch.placeNewPiece('b', 7, new Pawn   (Color.BLACK));
        chessMatch.placeNewPiece('c', 7, new Pawn   (Color.BLACK));
        chessMatch.placeNewPiece('d', 7, new Pawn   (Color.BLACK));
        chessMatch.placeNewPiece('e', 7, new Pawn   (Color.BLACK));
        chessMatch.placeNewPiece('f', 7, new Pawn   (Color.BLACK));
        chessMatch.placeNewPiece('g', 7, new Pawn   (Color.BLACK));
        chessMatch.placeNewPiece('h', 7, new Pawn   (Color.BLACK));

        chessMatch.setUpAvailableMovements();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialSetup();
        game.startMatch();
    }

}