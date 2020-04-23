package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public class RequestBooleanChoice extends PlayRequest {

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
