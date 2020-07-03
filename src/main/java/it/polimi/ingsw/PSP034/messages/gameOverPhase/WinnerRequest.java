package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import java.util.Objects;

/**
 * Message from the server to the client that communicates to a player that he/she won.
 */
public class WinnerRequest extends  GameOverRequest{
    static final long serialVersionUID = 681002356460L;

    private final String winner;
    private final String loser;

    /**
     * Initializes the message.
     * @param loser //TODO questo mi sa che non viene usato, come lo chiamo
     * @param winner Name of the winner.
     */
    public WinnerRequest(String loser, String winner){
        this.loser = Objects.requireNonNullElse(loser, "");
        this.winner = Objects.requireNonNullElse(winner, "");
    }

    public String getLoser() {
        return loser;
    }

    public String getWinner() {
        return winner;
    }
}
