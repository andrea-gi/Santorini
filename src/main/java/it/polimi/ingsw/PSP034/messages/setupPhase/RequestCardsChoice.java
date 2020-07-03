package it.polimi.ingsw.PSP034.messages.setupPhase;

/**
 * Message from the server to the client that requests to the player the choice of all the gods cards.
 */
public class RequestCardsChoice extends SetupRequest{
    static final long serialVersionUID = 8761634838437L;

    private final int playerNumber;

    /**
     * Initializes the message.
     *
     * @param playerNumber Number of players in the current game, so that the client knows how many cards to pick.
     */
    public RequestCardsChoice(int playerNumber){
        this.playerNumber = playerNumber;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
