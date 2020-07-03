package it.polimi.ingsw.PSP034.messages.playPhase;

/**
 * Message from the client to the server communicating a boolean answer of a player, in response to a RequestBooleanChoice.
 */
public class AnswerBooleanChoice extends PlayAnswer {
    static final long serialVersionUID = 624257738L;

    private final boolean choice;

    /**
     * Initializes the message.
     * @param choice The player's choice.
     */
    public AnswerBooleanChoice(boolean choice){
        this.choice = choice;
    }

    public boolean getChoice() {
        return this.choice;
    }
}
