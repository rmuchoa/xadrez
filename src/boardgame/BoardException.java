package boardgame;

import application.GameException;

public class BoardException extends GameException {
    public BoardException(String message) {
        super(message);
    }
}
