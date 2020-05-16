package it.polimi.ingsw.PSP034.messages.setupPhase;

import java.util.ArrayList;

public class RequestPersonalGod extends SetupRequest{
    static final long serialVersionUID = 102272495201L;

    private final String[] possibleGods;
    private final String[] alreadyChosenGods;

    public RequestPersonalGod(ArrayList<String> possibleGods, ArrayList<String> alreadyChosenGods){
        this.possibleGods = possibleGods.toArray(new String[0]);
        this.alreadyChosenGods = alreadyChosenGods.toArray(new String [0]);
    }

    public String[] getPossibleGods() {
        return possibleGods;
    }

    public String[] getAlreadyChosenGods() {
        return alreadyChosenGods;
    }
}
