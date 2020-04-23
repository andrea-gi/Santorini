package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public class RequestStart extends PlayRequest{

    public RequestStart(NextStateInfo actionInfo) {
        super(actionInfo);
    }

    public TurnPhase getNextPhase(){
        return super.getNextPhase();
    }
}
