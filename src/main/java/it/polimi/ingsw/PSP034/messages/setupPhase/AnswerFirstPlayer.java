package it.polimi.ingsw.PSP034.messages.setupPhase;

/**
 * Message from the client to the server that communicate the first player chosen.
 */
public class AnswerFirstPlayer extends SetupAnswer{
    private final String firstPlayer;

    /**
     * Initializes the message.
     *
     * @param firstPlayer Player chosen as the first player of the current game.
     */
    public AnswerFirstPlayer(String firstPlayer){
        this.firstPlayer = firstPlayer;
    }

    public String getFirstPlayer() {
        return firstPlayer;
    }
}
