package it.polimi.ingsw.PSP034.messages.setupPhase;

public class GodLikeInfo extends SetupRequest{
    private final String godLikePlayer;

    public GodLikeInfo(String godLikePlayer){
        this.godLikePlayer = godLikePlayer;
    }

    public String getGodLikePlayer() {
        return godLikePlayer;
    }
}
