package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;

/**
 * Interface implemented by each Request Manager (CLI or GUI)
 */
public interface RequestManager {
    /**
     * Handles a single request
     * @param message Request to be handled
     */
    void handleRequest(Request message);

}
