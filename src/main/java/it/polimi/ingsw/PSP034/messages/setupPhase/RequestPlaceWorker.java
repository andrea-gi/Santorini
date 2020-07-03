package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.SlimBoard;

/**
 * Message from the server to the client that requests to the player the placing of a worker.
 */
public class RequestPlaceWorker extends SetupRequest{
    static final long serialVersionUID = 4320999485103L;

    private final Sex sex;
    private final SlimBoard slimBoard;

    /**
     * Initializes the message.
     *
     * @param sex Sex of the worker to be placed.
     * @param slimBoard Update of the board at the current moment.
     */
    public RequestPlaceWorker(Sex sex, SlimBoard slimBoard){
        this.sex = sex;
        this.slimBoard = slimBoard;
    }

    public Sex getSex() {
        return sex;
    }

    public SlimBoard getSlimBoard() {
        return slimBoard;
    }
}
