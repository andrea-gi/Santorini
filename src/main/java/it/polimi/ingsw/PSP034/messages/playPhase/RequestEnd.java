package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

/**
 * Message from the server to the client that communicate to a player the end of his/her turn.
 */
public class RequestEnd extends PlayRequest{
    static final long serialVersionUID = 3143799613L;

    /**
     * Initializes the message.
     */
    public RequestEnd(){
        super(new NextStateInfo(TurnPhase.END));
    }
}
