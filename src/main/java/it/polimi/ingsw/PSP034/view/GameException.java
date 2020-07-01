package it.polimi.ingsw.PSP034.view;

/**
 * A custom exception that contains tabulated error code.
 */
public class GameException extends Exception{
    private final String code;

    /**
     * Creates the exception with the code error and the relative error description.
     * @param code Code of the error.
     * @param description Description of the error.
     */
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
