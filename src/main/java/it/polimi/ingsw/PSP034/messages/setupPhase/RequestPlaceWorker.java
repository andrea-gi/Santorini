package it.polimi.ingsw.PSP034.messages.setupPhase;

import it.polimi.ingsw.PSP034.constants.Sex;

public class RequestPlaceWorker extends SetupRequest{
    private final Sex sex;

    public RequestPlaceWorker(Sex sex){
        this.sex = sex;
    }

    public Sex getSex() {
        return sex;
    }

}
