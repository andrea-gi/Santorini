package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import it.polimi.ingsw.PSP034.constants.PlayerColor;

public class SingleLoserInfo extends GameOverRequest {
    static final long serialVersionUID = 842100053182L;

    private final String loser;
    private final PlayerColor color;

    public SingleLoserInfo(String loser, PlayerColor color){
        this.loser = loser;
        this.color = color;
    }

    public PlayerColor getColor() {
        return color;
    }

    public String getLoser() {
        return loser;
    }
}
