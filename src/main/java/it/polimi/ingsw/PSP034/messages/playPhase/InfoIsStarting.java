package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

public class InfoIsStarting extends PlayRequest{
    static final long serialVersionUID = 1258835918520L;
    private final String player;

    public InfoIsStarting(String player) {
        super(new NextStateInfo(TurnPhase.START));
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
