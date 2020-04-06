package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.model.*;

/**Handles the turn phases for each Player */
public class TurnHandler {
    private ITurnHandler currentGod;
    private Controller controller;
    private TurnPhase myTurnPhase;

    /**Sets the current God associated to the Player who is playing right now
     * @param currentGod is the God associated to the Player*/
    public void setCurrentGod(ITurnHandler currentGod) {
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
        myTurnPhase = currentGod.nextState();
    }

    /**Executes the actions in the actual turn phase*/
    public void executeSelectedState(Message fromServer){
        //spacchetto il messaggio
        //TurnPhase turnPhase = fromServer.getTurnPhase();
        //Player player = fromServer.getPlayer();
        //Worker worker = fromServer.getWorker();
        //Tile tile = fromServer.getTile();
        currentGod.executeState(turnPhase, player, worker, tile);
        }


    public boolean anyValidMove(Worker worker){
        boolean anyMove = false;
        for(Tile neighbour: worker.getMyTile().getNeighbouringTiles()){
            boolean valid = controller.getRules().validMove(controller.getCurrentGame().getCurrentPlayer(), worker, neighbour);
            if (valid){
                anyMove = true;
                break;
            }
        }
        return anyMove;
    }

    public boolean checkMoveLost(Player player){
        boolean lost = true;
        for (Worker worker :
                player.getMyWorkers()) {
            lost = anyValidMove(worker);
            if (!lost){
              break;
            }
        }
        return lost;
    }

    public boolean anyValidBuild(Worker worker){
        boolean anyBuild = false;
        for(Tile neighbour: worker.getMyTile().getNeighbouringTiles()){
            boolean valid = controller.getRules().validBuild(controller.getCurrentGame().getCurrentPlayer(), worker, neighbour);
            if (valid){
                anyBuild = true;
                break;
            }
        }
        return anyBuild;
    }

    public boolean checkBuildLost(Player player){
        boolean lost = true;
        for (Worker worker :
                player.getMyWorkers()) {
            lost = anyValidBuild(worker);
            if (!lost){
                break;
            }
        }
        return lost;
    }


}