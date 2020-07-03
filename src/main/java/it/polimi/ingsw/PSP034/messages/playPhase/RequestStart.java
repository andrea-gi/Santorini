package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

/**
 * Request form the server to the client that communicates to a player that he/she can start his/her turn.
 */
public class RequestStart extends PlayRequest{
    static final long serialVersionUID = 3485631072L;

    /**
     * Initializes the message.
     * @param actionInfo Information on the next action that needs to be executed.
     */
    public RequestStart(NextStateInfo actionInfo) {
        super(actionInfo);
    }

    public TurnPhase getNextPhase(){
        return super.getNextPhase();
    }
}
