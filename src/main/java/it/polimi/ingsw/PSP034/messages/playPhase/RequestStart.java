package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public class RequestStart extends PlayRequest{
    static final long serialVersionUID = 3485631072L;

    public RequestStart(NextStateInfo actionInfo) {
        super(actionInfo);
    }

    public TurnPhase getNextPhase(){
        return super.getNextPhase();
    }
}
