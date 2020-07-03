package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.PlayerColor;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.observer.ModelObserver;

/**
 * Interface implemented by each ClientHandler.
 */
public interface IClientConnection extends ModelObserver {
    /**
     * Closes the socket connection.
     */
    void closeConnection();

    /**
     * Sends an asynchronous {@link Request} message through an Output Stream.
     *
     * @param request {@link java.io.Serializable} message being sent.
     */
    void asyncSend(Request request);

    /**
     * Returns the player's name associated to the socket.
     *
     * @return Player's name.
     */
    String getName();

    /**
     * Sets the player's name associated to the socket.
     *
     * @param name Player's name.
     */
    void setName(String name);

    /**
     * Sets the player's color (ANSI) used for logging.
     *
     * @param color Color chosen.
     */
    void setDebugColor(PlayerColor color);

    /**
     * Returns the player's color (ANSI) for logging.
     *
     * @return Player's color.
     */
    String getDebugColor();

    /**
     * Checks if a player is an external viewer of the game (player who has already lost).
     *
     * @return {@code true} if player is an external viewer.
     */
    boolean isExternalViewer();
}
