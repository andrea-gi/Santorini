package it.polimi.ingsw.PSP034.messages.serverConfiguration;

/**
 * Message from the client to the server that communicates how many players were selected for a game.
 */
public class AnswerNumber extends AnswerServerConfig{
    static final long serialVersionUID = 3386261384L;

    private final int playerNumber;

    /**
     * Initializes the message.
     * @param playerNumber Number of player the game will be created for.
     */
    public AnswerNumber(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
