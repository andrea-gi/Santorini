package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.observer.ModelObserver;

/**
 * Interface implemented by each ClientHandler.
 */
public interface IClientConnection extends ModelObserver {
    /**
     * Closes the socket connection
     */
    void closeConnection();

    /**
     * Sends an asynchronous {@link Request} message through an {@link java.io.ObjectOutputStream}
     * @param request {@link java.io.Serializable} message being sent
     */
    void asyncSend(Request request);

    /**
     * Returns the player name associated to the socket
     * @return Player name
     */
    String getName();

    /**
     * Sets the player name associated to the socket
     * @param name Player name
     */
    void setName(String name);
}
