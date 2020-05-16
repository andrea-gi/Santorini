package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import it.polimi.ingsw.PSP034.constants.Color;

public class SingleLoserInfo extends GameOverRequest {
    static final long serialVersionUID = 842100053182L;

    private final String loser;
    private final Color color;

    public SingleLoserInfo(String loser, Color color){
        this.loser = loser;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getLoser() {
        return loser;
    }
}
