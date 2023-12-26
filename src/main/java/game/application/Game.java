package game.application;

import game.chess.ChessBoard;
import game.chess.ChessMatch;
import game.chess.ChessPiece;
import game.chess.ChessPosition;
import game.chess.Color;
import game.chess.pieces.Bishop;
import game.chess.pieces.King;
import game.chess.pieces.Knight;
import game.chess.pieces.Pawn;
import game.chess.pieces.Queen;
import game.chess.pieces.Rook;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Scanner scanner;
    private final ChessMatch chessMatch;
    private final ChessBoard chessBoard;
    private final List<ChessPiece> capturedPieces;

    public Game() {
        scanner = new Scanner(System.in);
        chessBoard = ChessBoard.builder().build();
        chessMatch = new ChessMatch(chessBoard);
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
            } catch (InputMismatchException ex) {
                System.out.println(ex.getMessage());
                scanner.nextLine();
            }
        }

        UI.clearScreen();
        UI.printMatch(chessMatch, capturedPieces);

    }

    private void initialSetup() {
        chessMatch.placeNewPiece('a', 1, Rook   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('b', 1, Knight .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('c', 1, Bishop .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('d', 1, Queen  .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('e', 1, King   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('f', 1, Bishop .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('g', 1, Knight .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('h', 1, Rook   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());

        chessMatch.placeNewPiece('a', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('b', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('c', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('d', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('e', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('f', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('g', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());
        chessMatch.placeNewPiece('h', 2, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.WHITE).build());

        chessMatch.placeNewPiece('a', 8, Rook   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('b', 8, Knight .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('c', 8, Bishop .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('d', 8, Queen  .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('e', 8, King   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('f', 8, Bishop .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('g', 8, Knight .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('h', 8, Rook   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());

        chessMatch.placeNewPiece('a', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('b', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('c', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('d', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('e', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('f', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('g', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
        chessMatch.placeNewPiece('h', 7, Pawn   .builder().board(chessBoard).match(chessMatch).color(Color.BLACK).build());
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initialSetup();
        game.startMatch();
    }

}