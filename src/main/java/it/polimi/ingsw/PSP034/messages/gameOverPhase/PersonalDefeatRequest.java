package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import java.util.Objects;

public class PersonalDefeatRequest extends GameOverRequest{
    static final long serialVersionUID = 93159038581L;

    private final String winner;
    private final String[] losers;

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
