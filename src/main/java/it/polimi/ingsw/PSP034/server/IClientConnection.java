package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.observer.ModelObserver;

public interface IClientConnection extends ModelObserver {
    void closeConnection();

    void asyncSend(Request request);

    String getName();

    void setName(String name);
}
