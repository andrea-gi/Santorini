package it.polimi.ingsw.PSP034.messages.clientConfiguration;


public class ErrorMessage extends RequestClientConfig {
    static final long serialVersionUID = 785718527L;
    private final String code;
    private final String description;

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
