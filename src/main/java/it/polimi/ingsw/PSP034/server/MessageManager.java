package it.polimi.ingsw.PSP034.server;

import it.polimi.ingsw.PSP034.constants.GamePhase;
import it.polimi.ingsw.PSP034.controller.Controller;
import it.polimi.ingsw.PSP034.controller.GameOverPhase;
import it.polimi.ingsw.PSP034.messages.Answer;
import it.polimi.ingsw.PSP034.messages.PlayPhase.PlayAnswer;
import it.polimi.ingsw.PSP034.messages.PlayPhase.PlayRequest;
import it.polimi.ingsw.PSP034.messages.Request;
import it.polimi.ingsw.PSP034.messages.SetupPhase.SetupAnswer;
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
        debugGame.sendingTo = player;
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
            //else
                // TODO -- DEVO CHIAMARE SETUP EXECUTE controller.
        }
        else if (message instanceof PlayAnswer){
            if (controller.getGamePhase() != GamePhase.PLAY)
                validMessage = false;
            else
                controller.getTurnHandler().executeSelectedState((PlayAnswer) message);
        }
        /* TODO -- else if (message instanceof GameOverAnswer)

        TODO -- eventuale gestione errori di tipo (?)


         */

    }


}
