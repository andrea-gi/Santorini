package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import java.util.Objects;

public class WinnerRequest extends  GameOverRequest{
    static final long serialVersionUID = 681002356460L;

    private final String loser;

    public WinnerRequest(String loser){
        this.loser = Objects.requireNonNullElse(loser, "");
    }

    public String getLoser() {
        return loser;
    }
}
