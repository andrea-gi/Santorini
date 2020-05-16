package it.polimi.ingsw.PSP034.messages.setupPhase;

public class GodLikeInfo extends SetupRequest{
    static final long serialVersionUID = 8042419190858L;

    private final String godLikePlayer;

    public GodLikeInfo(String godLikePlayer){
        this.godLikePlayer = godLikePlayer;
    }

    public String getGodLikePlayer() {
        return godLikePlayer;
    }
}
