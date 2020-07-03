package it.polimi.ingsw.PSP034.messages.clientConfiguration;

/**
 * Message that communicates that an error occurred.
 */
public class ErrorMessage extends RequestClientConfig {
    static final long serialVersionUID = 785718527L;
    private final String code;
    private final String description;

    /**
     * Initializes the message with the error information.
     * @param code Code of the error.
     * @param description Description of th error.
     */
    public ErrorMessage(String code, String description){
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
