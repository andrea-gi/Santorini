package it.polimi.ingsw.PSP034.messages.SetupPhase;

import java.util.ArrayList;

public class RequestPersonalGod extends SetupRequest{
    private final String[] possibleGods;

    public RequestPersonalGod(ArrayList<String> possibleGods){
        this.possibleGods = new String[possibleGods.size()];
        for (int i = 0; i < possibleGods.size(); i++){
            this.possibleGods[i] = possibleGods.get(i);
        }
    }

    public String[] getPossibleGods() {
        return possibleGods;
    }
}
