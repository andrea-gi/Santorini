package it.polimi.ingsw.PSP034.messages.setupPhase;

/**
 * Message from the client to the server that communicate the personal god chosen by the sender.
 */
public class AnswerPersonalGod extends SetupAnswer{
    static final long serialVersionUID = 1765935412417L;

    private final String myGod;

    /**
     * Initializes the message.
     *
     * @param myGod God chosen by the player.
     */
    public AnswerPersonalGod(String myGod){
        this.myGod = myGod;
    }

    public String getMyGod() {
        return myGod;
    }
}
