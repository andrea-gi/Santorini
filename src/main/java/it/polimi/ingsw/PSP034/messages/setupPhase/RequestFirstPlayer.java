package it.polimi.ingsw.PSP034.messages.setupPhase;

import java.util.ArrayList;

/**
 * Message from the server to the client that requests to the player the first player choice.
 */
public class RequestFirstPlayer extends SetupRequest {
    static final long serialVersionUID = 50627748491L;

    private final String[] players;

    /**
     * Initializes the message.
     *
     * @param players List of players where to pick from the first player.
     */
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
