package it.polimi.ingsw.PSP034.messages.setupPhase;

public class FirstPlayerInfo extends SetupRequest{
    static final long serialVersionUID = 898660326274L;

    private final String firstPlayer;

    public FirstPlayerInfo(String firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }
}
