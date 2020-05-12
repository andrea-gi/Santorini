package it.polimi.ingsw.PSP034.messages.serverConfiguration;

import it.polimi.ingsw.PSP034.messages.Request;

public class RequestServerConfig extends Request {
    private final ServerInfo info;

    public RequestServerConfig(ServerInfo info) {
        this.info = info;
    }

    public ServerInfo getInfo() {
        return info;
    }
}
