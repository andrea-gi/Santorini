package it.polimi.ingsw.PSP034.messages.gameOverPhase;

/**
 * Message sent by the server to the client that communicates another player's disconnection.
 */
public class EndByDisconnection extends GameOverRequest{
    static final long serialVersionUID = 199577672281L;

    private final String disconnectedPlayer;

    /**
     * Initializes the message
     * @param disconnectedPlayer Name of the disconnected player.
     */
    public EndByDisconnection(String disconnectedPlayer){
        this.disconnectedPlayer = disconnectedPlayer;
    }

    public String getDisconnectedPlayer() {
        return disconnectedPlayer;
    }
}
