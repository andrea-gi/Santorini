package it.polimi.ingsw.PSP034.messages.clientConfiguration;

public class RequestIP extends RequestClientConfig{
    static final long serialVersionUID = 98112518522L;
    private final boolean error;

    public RequestIP(boolean error){
        this.error = error;
    }

    public boolean getError() {
        return error;
    }
}
