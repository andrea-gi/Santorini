package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.messages.SlimBoard;

/**
 * Message from server to client initializing the board for the first time.
 */
public class InitializeBoard extends SetupRequest {
    static final long serialVersionUID = 942624405753L;

    private final SlimBoard slimBoard;

    /**
     * Initializes the message.
     *
     * @param slimBoard Starting board.
     */
    public InitializeBoard(SlimBoard slimBoard){
        this.slimBoard = slimBoard;
    }

    public SlimBoard getSlimBoard() {
        return slimBoard;
    }
}
