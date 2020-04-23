package it.polimi.ingsw.PSP034.messages.setupPhase;

public class AnswerFirstPlayer extends SetupAnswer{
    private final String firstPlayer;

    public AnswerFirstPlayer(String firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }
}
