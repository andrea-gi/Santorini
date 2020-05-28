package it.polimi.ingsw.PSP034.view;

public class GameException extends Exception{
    private final int code;
    public GameException(String message, int code){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
