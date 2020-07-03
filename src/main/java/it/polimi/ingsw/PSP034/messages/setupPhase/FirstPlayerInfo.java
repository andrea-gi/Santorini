package it.polimi.ingsw.PSP034.messages.setupPhase;

/**
 * Message from the server to the client that communicates which is the first player to start the current game.
 */
public class FirstPlayerInfo extends SetupRequest{
    static final long serialVersionUID = 898660326274L;

    private final String firstPlayer;

    /**
     * Initializes the message.
     *
     * @param firstPlayer Player who starts the current game.
     */
    public FirstPlayerInfo(String firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }
}
