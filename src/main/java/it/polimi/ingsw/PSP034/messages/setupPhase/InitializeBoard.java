package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.messages.SlimBoard;

public class InitializeBoard extends SetupRequest {
    private final SlimBoard slimBoard;

    public InitializeBoard(SlimBoard slimBoard){
        this.slimBoard = slimBoard;
    }

    public SlimBoard getSlimBoard() {
        return slimBoard;
    }
}
