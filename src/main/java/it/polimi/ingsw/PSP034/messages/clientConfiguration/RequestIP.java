package it.polimi.ingsw.PSP034.messages.clientConfiguration;

/**
 * Request internal to the client to ask for the address of the server to connect to.
 */
public class RequestIP extends RequestClientConfig{
    static final long serialVersionUID = 98112518522L;
    private final boolean error;

    /**
     * Initializes the request.
     * @param error Whether there was an error the last time the client tried to connect to a server.
     */
    public RequestIP(boolean error){
        this.error = error;
    }

    public boolean getError() {
        return error;
    }
}
