package it.polimi.ingsw.PSP034.messages.clientConfiguration;


public class ErrorMessage extends RequestClientConfig {
    static final long serialVersionUID = 785718527L;
    private int code;

    public ErrorMessage(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
