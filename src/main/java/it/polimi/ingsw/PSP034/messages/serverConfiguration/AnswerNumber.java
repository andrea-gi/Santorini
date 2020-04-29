package it.polimi.ingsw.PSP034.messages.serverConfiguration;

public class AnswerNumber extends AnswerServerConfig{
    private final int playerNumber;

    public AnswerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
