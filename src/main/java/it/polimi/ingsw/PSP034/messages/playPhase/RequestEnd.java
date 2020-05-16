package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public class RequestEnd extends PlayRequest{
    static final long serialVersionUID = 3143799613L;

    public RequestEnd(){
        super(new NextStateInfo(TurnPhase.END));
    }
}
