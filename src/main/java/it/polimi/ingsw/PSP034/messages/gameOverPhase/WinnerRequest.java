package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import java.util.Objects;

public class WinnerRequest extends  GameOverRequest{
    static final long serialVersionUID = 681002356460L;

    private final String winner;
    private final String loser;

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
