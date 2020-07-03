package it.polimi.ingsw.PSP034.messages.setupPhase;

/**
 * Message from the server to the client that communicates which is the player starting to play in this turn.
 */
public class InfoIsPlacing extends SetupRequest{
    static final long serialVersionUID = 9218835918525L;
    private final String player;

    /**
     * Initializes the message.
     *
     * @param player Player starting his turn.
     */
    public InfoIsPlacing(String player){
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
