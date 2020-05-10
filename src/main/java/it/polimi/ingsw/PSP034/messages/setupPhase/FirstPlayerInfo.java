package it.polimi.ingsw.PSP034.messages.setupPhase;

public class FirstPlayerInfo extends SetupRequest{
    private final String firstPlayer;

    public FirstPlayerInfo(String firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }
}
