package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import java.util.Objects;

public class PersonalDefeatRequest extends GameOverRequest{
    static final long serialVersionUID = 93159038581L;

    private final String winner;

    public PersonalDefeatRequest(String winner){
        this.winner = Objects.requireNonNullElse(winner, "");
    }

    public String getWinner() {
        return winner;
    }
}
