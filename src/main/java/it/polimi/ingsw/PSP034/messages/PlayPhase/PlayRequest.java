package it.polimi.ingsw.PSP034.messages.PlayPhase;


import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.Request;

public abstract class PlayRequest extends Request {
    private final NextStateInfo actionInfo;

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
