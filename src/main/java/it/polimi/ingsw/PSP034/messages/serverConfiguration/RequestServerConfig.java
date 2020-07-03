package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.messages.Request;

/**
 * Message from the server to the client during the registration phase.
 */
public class RequestServerConfig extends Request {
    static final long serialVersionUID = 37143776839L;

    private final ServerInfo info;

    /**
     * Initializes the message.
     * @param info State of the server configuration phase.
     */
    public RequestServerConfig(ServerInfo info) {
        this.info = info;
    }

    public ServerInfo getInfo() {
        return info;
    }
}
