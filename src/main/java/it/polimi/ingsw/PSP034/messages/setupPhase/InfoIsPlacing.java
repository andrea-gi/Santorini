package it.polimi.ingsw.PSP034.messages.setupPhase;

public class InfoIsPlacing extends SetupRequest{
    private final String player;

    public InfoIsPlacing(String player){
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
