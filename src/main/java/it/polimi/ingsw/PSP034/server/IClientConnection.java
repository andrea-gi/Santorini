package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.messages.Request;

public interface IClientConnection {
    void closeConnection();

    void asyncSend(Request request);

    String getName();

    void setName(String name);
}
