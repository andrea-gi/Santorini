package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public class RequestEnd extends PlayRequest{
    public RequestEnd(){
        super(new NextStateInfo(TurnPhase.END));
    }
}
