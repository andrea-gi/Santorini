package it.polimi.ingsw.PSP034.view;

public class GameException extends Exception{
    private final String code;

    public GameException(String code, String description){
        super(description);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
