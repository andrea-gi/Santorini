package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import it.polimi.ingsw.PSP034.constants.PlayerColor;

/**
 * Message from the server to the client that communicates to a player that another player lost the game (but there is no winner yet).
 */
public class SingleLoserInfo extends GameOverRequest {
    static final long serialVersionUID = 842100053182L;

    private final String loser;
    private final PlayerColor color;

    /**
     * Initializes the message.
     * @param loser Name of the player that lost.
     * @param color Color of the Player that lost.
     */
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
