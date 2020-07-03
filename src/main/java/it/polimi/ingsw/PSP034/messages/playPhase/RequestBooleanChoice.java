package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

/**
 * Message from the server to the client that requests to the player a boolean choice.
 */
public class RequestBooleanChoice extends PlayRequest {
    static final long serialVersionUID = 205810564L;

    /**
     * Initializes the message.
     * @param info Information on the next action that needs to be executed.
     */
    public RequestBooleanChoice(NextStateInfo info){
        super(info);
    }

    @Override
    public TurnPhase getNextPhase() {
        return super.getNextPhase();
    }

    @Override
    public RequiredActions[] getRequiredActions() {
        return super.getRequiredActions();
    }
}
