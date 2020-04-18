package it.polimi.ingsw.PSP034.messages.toClient;

import it.polimi.ingsw.PSP034.messages.NextStateInfo;

public class RequestBooleanChoice extends ToClientMessage{
    private final NextStateInfo actionInfo;

    public RequestBooleanChoice(NextStateInfo info){
        this.actionInfo = info;
    }

    public NextStateInfo getActionInfo() {
        return actionInfo;
    }
}
