package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.messages.SlimBoard;

public class RequestPlaceWorker extends SetupRequest{
    private final Sex sex;
    private final SlimBoard slimBoard;

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
