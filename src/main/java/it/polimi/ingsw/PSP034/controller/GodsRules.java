package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class GodsRules implements IRules, ITurnHandler {
    private static TurnPhase currentTurnPhase; //dobbiamo definire come utilizzare questo, static --> deve essere azzerato ad ogni cambio player
    private static DefaultRules defaultRule; //FINAL????
    private IRules decoratedRules;


    public GodsRules(IRules decoratedRules){
        this.decoratedRules = decoratedRules;
        if (decoratedRules instanceof DefaultRules){ // tengo traccia della prima decorazione, quella "core"
            defaultRule = (DefaultRules) decoratedRules;
        }
    }

    //TODO -- nextState() and executeState()
    @Override
    public TurnPhase nextState(){
        return null;
        //CAMBIARE
    };
    @Override
    public Boolean executeState(){// CAMBIARE
        return false;
    };


    @Override
    public void move(Worker worker, Tile destinationTile){
        decoratedRules.move(worker, destinationTile);
    };

    @Override
    public void build(Tile buildTile){
        decoratedRules.build(buildTile);
    };


    @Override
    public boolean validMove(Player player, Worker worker, Tile destinationTile){
        if ( !(decoratedRules instanceof DefaultRules) ) {
            return true;
        }
        else{
            return (decoratedRules.validMove(player, worker, destinationTile));
        }
    };

    @Override
    public boolean validBuild(Player player, Worker worker, Tile buildingTile){
        if ( !(decoratedRules instanceof DefaultRules) ) {
            return true;
        }
        else{
            return (decoratedRules.validMove(player, worker, buildingTile));
        }
    };
}
