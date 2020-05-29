package it.polimi.ingsw.PSP034.messages.gameOverPhase;

public class EndByDisconnection extends GameOverRequest{
    static final long serialVersionUID = 199577672281L;

    private final String disconnectedPlayer;

    public EndByDisconnection(String disconnectedPlayer){
        this.disconnectedPlayer = disconnectedPlayer;
    }

    public String getDisconnectedPlayer() {
        return disconnectedPlayer;
    }
}
