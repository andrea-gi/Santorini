package it.polimi.ingsw.PSP034.messages.gameOverPhase;

import it.polimi.ingsw.PSP034.model.Player;

public class SendGameOver extends GameOverRequest{
    private final Player loser;

    public SendGameOver(Player loser){
        this.loser = loser;
    }

    public Player getLoser() {
        return loser;
    }
}
