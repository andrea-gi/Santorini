package it.polimi.ingsw.PSP034.messages.setupPhase;

public class InfoIsPlacing extends SetupRequest{
    static final long serialVersionUID = 9218835918525L;
    private final String player;

    public InfoIsPlacing(String player){
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
