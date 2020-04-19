package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.messages.PlayPhase.PlayRequest;
import it.polimi.ingsw.PSP034.model.Player;

public class MessageManager {
    private final Controller controller;

    public MessageManager(Controller controller){
        this.controller = controller;
    }

    protected void sendToPlayer(Player player, PlayRequest message){

    }

    protected void sendToPlayer(Player player, OtherMessage message){

    }
}
