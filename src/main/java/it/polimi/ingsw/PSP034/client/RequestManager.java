package it.polimi.ingsw.PSP034.client;

import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.clientConfiguration.ErrorMessage;
import it.polimi.ingsw.PSP034.view.GameException;

/**
 * Abstract class extended by each Request Manager (CLI or GUI)
 */
public abstract class RequestManager {
    private ClientGameHandler sender;

    /**
     * Handles a single request
     * @param message Request to be handled
     */
    public abstract void handleRequest(Request message) throws GameException;

    public abstract void showError(ErrorMessage error);

    /**
     * Sets instance used to send answers.
     * @param sender Reference to ClientGameHandler used by a client
     */
    public void setSender(ClientGameHandler sender) {
        this.sender = sender;
    }

    /**
     * Sends an answer through a previously set {@link ClientGameHandler}.
     * @param answer Answer to be sent.
     */
    public void sendAnswer(Answer answer){
        if (sender != null)
            sender.send(answer);
    }

    /**
     * Returns message handling availability.
     * @return {@code true} if {@link this#handleRequest(Request)} can manage a new request.
     */
    public abstract boolean canHandleRequest();
}
