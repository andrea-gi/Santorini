package it.polimi.ingsw.PSP034.messages.setupPhase;

/**
 * Message from the client to the server that communicate the gods chosen by the most god like player.
 */
public class AnswerCardsChoice extends SetupAnswer{
    private final String[] choice;

    /**
     * Initializes the message.
     *
     * @param choice List of the chosen gods names.
     */
    public AnswerCardsChoice(String[] choice){
        this.choice = choice;
    }

    public String[] getChoice() {
        return choice;
    }
}
