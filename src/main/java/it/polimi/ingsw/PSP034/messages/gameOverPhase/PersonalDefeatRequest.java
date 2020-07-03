package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import java.util.Objects;

/**
 * Message sent from the server to the client that communicates to a player tha he/she lost the game and who is the winner, if there is one.
 */
public class PersonalDefeatRequest extends GameOverRequest{
    static final long serialVersionUID = 93159038581L;

    private final String winner;
    private final String[] losers;

    /**
     * Initializes the message
     * @param winner Name of the winner. If there is no winner, this value must be {@code null}.
     * @param losers List of the names of all the losers.
     */
    public PersonalDefeatRequest(String winner, String... losers){
        this.winner = Objects.requireNonNullElse(winner, "");
        if (losers != null) {
            this.losers = losers.clone();
        }
        else{
            this.losers = new String[0];
        }
    }

    public String getWinner() {
        return winner;
    }

    public String[] getLosers() {
        return losers;
    }
}
