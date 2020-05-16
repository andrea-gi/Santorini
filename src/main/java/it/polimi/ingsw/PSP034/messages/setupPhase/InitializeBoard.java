package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.messages.SlimBoard;

public class InitializeBoard extends SetupRequest {
    static final long serialVersionUID = 942624405753L;

    private final SlimBoard slimBoard;

    public InitializeBoard(SlimBoard slimBoard){
        this.slimBoard = slimBoard;
    }

    public SlimBoard getSlimBoard() {
        return slimBoard;
    }
}
