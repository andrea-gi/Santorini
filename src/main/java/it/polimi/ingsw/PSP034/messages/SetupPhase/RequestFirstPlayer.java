package it.polimi.ingsw.PSP034.messages.SetupPhase;

import it.polimi.ingsw.PSP034.model.Player;

import java.util.ArrayList;

public class RequestFirstPlayer extends SetupRequest {
    private final String[] players;

    public RequestFirstPlayer(ArrayList<String> players){
        this.players = new String[players.size()];
        for (int i = 0; i < players.size(); i++){
            this.players[i] = players.get(i);
        }
    }
}
