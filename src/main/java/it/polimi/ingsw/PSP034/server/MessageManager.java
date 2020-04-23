package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.controller.Controller;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.gameOverPhase.GameOverAnswer;
import it.polimi.ingsw.PSP034.messages.playPhase.PlayAnswer;
import it.polimi.ingsw.PSP034.messages.playPhase.PlayRequest;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupAnswer;
import it.polimi.ingsw.PSP034.messages.setupPhase.SetupRequest;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.debugGame;
import it.polimi.ingsw.PSP034.observer.ServerObserver;

public class MessageManager implements ServerObserver {
    private final Controller controller;

    public MessageManager(Controller controller){
        this.controller = controller;
    }

    public void sendToPlayer(Player player, Request message){
        if(message instanceof PlayRequest)
            debugGame.playRequests.add((PlayRequest) message);
        if(message instanceof SetupRequest)
            debugGame.setupRequests.add((SetupRequest) message);
        debugGame.sendingTo.add(player.getName());
    }

    //protected void sendToPlayer(Player player, OtherMessage message){

    //}

    @Override
    public void update(Answer message, String name) {
        boolean validMessage = true;
        if (!controller.getCurrentPlayer().getName().equals(name)) {
            validMessage = false;
            return; //Potrei mandare un messaggio "non è il tuo turno", da decidere
        }
        if (message instanceof SetupAnswer){
            if (controller.getGamePhase() != GamePhase.SETUP)
                validMessage = false;
            else
                controller.executeSelectedState((SetupAnswer) message);
        }
        else if (message instanceof PlayAnswer){
            if (controller.getGamePhase() != GamePhase.PLAY)
                validMessage = false;
            else
                controller.executeSelectedState((PlayAnswer) message);
        }
        // TODO -- else if (message instanceof GameOverAnswer)
        else if (message instanceof GameOverAnswer){
            if (controller.getGamePhase() != GamePhase.GAMEOVER)
                validMessage = false;
            else
                controller.executeSelectedState((GameOverAnswer) message);
        }
        //TODO -- eventuale gestione errori di tipo (?)




    }


}
