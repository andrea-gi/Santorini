package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.toClient.RequestAction;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IStateManager;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.observer.Observer;

import java.util.ArrayList;

/**Handles the turn phases for each Player */
public class TurnHandler {
    private IStateManager currentGod; //DA SALVARE NEL MODEL ??
    private final Controller controller;
    private TurnPhase myTurnPhase; //DA SALVARE NEL MODEL ??

    public TurnHandler(Controller controller){
        this.controller = controller;
    }

    /**Sets the current God associated to the Player who is playing right now
     * @param currentGod is the God associated to the Player*/
    public void setCurrentGod(IStateManager currentGod) {
        this.currentGod = currentGod;
    }

    /**Sets the right turn phase
     * @param turnPhase is the turn phase that needs to be executed at the moment*/
    public void setMyTurnPhase(TurnPhase turnPhase){
        this.myTurnPhase = turnPhase;
    }

    public TurnPhase getMyTurnPhase(){
        return myTurnPhase;
    }

    /**Makes the state of the turn change in order, depending on the God associated to the Player */
    public void receivedNextState(){
        //myTurnPhase = currentGod.nextState();
        NextStateInfo nextStateInfo = currentGod.nextState(myTurnPhase);
        switch (nextStateInfo.getNextPhase()){
            case BUILD:
            case MOVE:
                sendToPlayer(player, new RequestAction(nextStateInfo, player));


        //sendToPlayer(player, Messaggio)
        }
    }




    //**Executes the actions in the actual turn phase*/
    //public void executeSelectedState(Message fromServer){
        //spacchetto il messaggio
        //TurnPhase turnPhase = fromServer.getTurnPhase();
        //Player player = fromServer.getPlayer();
        //Worker worker = fromServer.getWorker();
        //Tile tile = fromServer.getTile();
        //currentGod.executeState(turnPhase, player, worker, tile);
        //}
}