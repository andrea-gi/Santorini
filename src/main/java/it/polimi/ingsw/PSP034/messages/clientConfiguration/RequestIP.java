package it.polimi.ingsw.PSP034.messages.clientConfiguration;

public class RequestIP extends RequestClientConfig{
    private final boolean error;

    public RequestIP(boolean error){
        this.error = error;
    }

    public boolean getError() {
        return error;
    }
}
