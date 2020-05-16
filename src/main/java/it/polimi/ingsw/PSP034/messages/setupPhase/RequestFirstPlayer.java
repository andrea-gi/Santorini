package it.polimi.ingsw.PSP034.messages.setupPhase;

import java.util.ArrayList;

public class RequestFirstPlayer extends SetupRequest {
    static final long serialVersionUID = 50627748491L;

    private final String[] players;

    public RequestFirstPlayer(ArrayList<String> players){
        this.players = new String[players.size()];
        for (int i = 0; i < players.size(); i++){
            this.players[i] = players.get(i);
        }
    }

    public String[] getPlayers() {
        return players;
    }
}
