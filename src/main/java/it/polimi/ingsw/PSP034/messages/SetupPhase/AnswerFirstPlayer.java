package it.polimi.ingsw.PSP034.messages.SetupPhase;

public class AnswerFirstPlayer extends SetupAnswer{
    private String firstPlayer;

    public AnswerFirstPlayer(String firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }
}
