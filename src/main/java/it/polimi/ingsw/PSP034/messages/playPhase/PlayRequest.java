package it.polimi.ingsw.PSP034.messages.playPhase;


import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.Request;

/**
 * Answer from the server to the client, during the play phase of a game.
 */
public abstract class PlayRequest extends Request {
    private final NextStateInfo actionInfo;

    /**
     * Initializes the message.
     * @param actionInfo Information on the next action that needs to be executed.
     */
    protected PlayRequest(NextStateInfo actionInfo) {
        this.actionInfo = actionInfo;
    }

    protected NextStateInfo getActionInfo(){
        return actionInfo;
    }

    public TurnPhase getNextPhase(){
        return actionInfo.getNextPhase();
    }

    public RequiredActions[] getRequiredActions(){
        return actionInfo.getRequiredActions();
    }
}
