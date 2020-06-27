package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.controller.Controller;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.GameOverAnswer;
import it.polimi.ingsw.PSP034.messages.playPhase.PlayAnswer;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.playPhase.PlayRequest;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupAnswer;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupRequest;
import it.polimi.ingsw.PSP034.server.Server;

public class MessageManager {
    private final Controller controller;
    private final Server server;

    protected MessageManager(Controller controller, Server server){
        this.controller = controller;
        this.server = server;
    }

    protected void sendTo(Request message, String... players){
        if (players == null)
            return;
        for (String player : players)
            server.asyncSendTo(player, message);
    }

    public void handleMessage(Answer message, String name) {
        if (!controller.getCurrentPlayer().getName().equals(name)) {
            return;
        }
        if (message instanceof SetupAnswer){
            if (controller.getGamePhase() == GamePhase.SETUP) {
                controller.executeSelectedState((SetupAnswer) message);
            }
        }
        else if (message instanceof PlayAnswer){
            if (controller.getGamePhase() == GamePhase.PLAY) {
                controller.executeSelectedState((PlayAnswer) message);
            }
        }
    }
}
