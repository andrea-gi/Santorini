package it.polimi.ingsw.PSP034.messages.serverConfiguration;

public class AnswerNumber extends AnswerServerConfig{
    static final long serialVersionUID = 3386261384L;

    private final int playerNumber;

    public AnswerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
