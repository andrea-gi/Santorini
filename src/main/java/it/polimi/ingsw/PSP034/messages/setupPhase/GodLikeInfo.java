package it.polimi.ingsw.PSP034.messages.setupPhase;

/**
 * Message from the server to the client that communicates which is the most god like player.
 */
public class GodLikeInfo extends SetupRequest{
    static final long serialVersionUID = 8042419190858L;

    private final String godLikePlayer;

    /**
     * Initializes the message.
     *
     * @param godLikePlayer Player found as most god like.
     */
    public GodLikeInfo(String godLikePlayer){
        this.godLikePlayer = godLikePlayer;
    }

    public String getGodLikePlayer() {
        return godLikePlayer;
    }
}
