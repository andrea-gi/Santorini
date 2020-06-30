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

/**
 * Checks if the messages are rightfully received and in that case it handles them (forwarding them to the
 * server or controller, depending on the situation).
 */
public class MessageManager {
    private final Controller controller;
    private final Server server;

    protected MessageManager(Controller controller, Server server){
        this.controller = controller;
        this.server = server;
    }

    /**
     * If the players are not null or invalid, forwards a sending request to the server.
     * @param message Message to be sent.
     * @param players Players recipient of the message.
     */
    protected void sendTo(Request message, String... players){
        if (players == null || players.length == 0)
            return;
        for (String player : players){
            server.asyncSendTo(player, message);
        }
    }

    /**
     * Checks if the message corresponds to the game phase: in that case it forwards it to the controller,
     * so that the controller itself can process the message.
     * @param message Message to handle.
     * @param name Name of the player receiving the message.
     */
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
