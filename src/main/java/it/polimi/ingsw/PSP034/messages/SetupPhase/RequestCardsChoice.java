package it.polimi.ingsw.PSP034.messages.SetupPhase;

public class RequestCardsChoice extends SetupRequest{
    private final int playerNumber;

    public RequestCardsChoice(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
