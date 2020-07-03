package it.polimi.ingsw.PSP034.messages.playPhase;

import it.polimi.ingsw.PSP034.constants.TurnPhase;

/**
 * Message from the server to the client that communicates that another player is starting his/her turn.
 */
public class InfoIsStarting extends PlayRequest{
    static final long serialVersionUID = 1258835918520L;
    private final String player;

    /**
     * Initializes the message.
     * @param player name of the player whose turn is starting.
     */
    public InfoIsStarting(String player) {
        super(new NextStateInfo(TurnPhase.START));
        this.player = player;
    }

    public String getPlayer() {
        return player;
    }
}
