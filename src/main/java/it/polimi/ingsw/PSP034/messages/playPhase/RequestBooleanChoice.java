package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public class RequestBooleanChoice extends PlayRequest {
    static final long serialVersionUID = 205810564L;

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
