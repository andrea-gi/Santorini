package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class GodsRules implements IRules, ITurnHandler {
    private static DefaultRules defaultRules;
    private static IRules completeRules;
    private IRules decoratedRules;

    public GodsRules(IRules decoratedRules){
        this.decoratedRules = decoratedRules;
        if (decoratedRules instanceof DefaultRules){ // Core decorated. Has default methods.
            defaultRules = (DefaultRules) decoratedRules;
        }
        completeRules = this;  // whole decorator the last addes
    }

    /*---------------------------*/
    /*-----Strategy methods------*/
    @Override
    public TurnPhase nextState(){
        throw new NullPointerException("There is no default nextState strategy. Wrong method call.");
    };

    @Override
    public Boolean executeState(TurnPhase currentPhase, Player player, Worker worker, Tile tile){
        throw new NullPointerException("There is no default executeState strategy. Wrong method call.");
    };

    /*---------------------------*/
    /*-----Decorator methods-----*/
    @Override
    public DefaultRules getDefaultRules() {
        return defaultRules;
    }

    @Override
    public Sex getChosenSex() {
        return defaultRules.getChosenSex();
    }

    @Override
    public void setChosenSex(Worker chosenWorker) {
        defaultRules.setChosenSex(chosenWorker);
    }

    @Override
    public Tile getPreviousTile(){ return defaultRules.getPreviousTile();}

    @Override
    public void setPreviousTile(Tile tile){ defaultRules.setPreviousTile(tile);}


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
        if ( decoratedRules instanceof DefaultRules ) {
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
            return (decoratedRules.validBuild(player, worker, buildingTile));
        }
    };

    @Override
    public boolean checkWin(Worker worker){
        return defaultRules.checkWin(worker);
    };

    @Override
    public boolean anyValidMove(Player player, Worker worker) {
        boolean anyMove = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validMove(player, worker, neighbour); //ATTENZIONE, REGOLE COMPLETE; USARE QUESTA!
            if (valid) {
                anyMove = true;
                break;
            }
        }
        return anyMove;
    }

    @Override
    public boolean checkMoveLost(Player player) {
        boolean lost = true;
        for (Worker worker :
                player.getMyWorkers()) {
            lost = this.anyValidMove(player, worker);
            if (!lost) {
                break;
            }
        }
        return lost;
    }

    @Override
    public boolean anyValidBuild(Player player, Worker worker) {
        boolean anyBuild = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validBuild(player, worker, neighbour); //ATTENZIONE, REGOLE COMPLETE; USARE QUESTA!!
            if (valid) {
                anyBuild = true;
                break;
            }
        }
        return anyBuild;
    }

    @Override
    public boolean checkBuildLost(Player player) {
        boolean lost = true;
        for (Worker worker :
                player.getMyWorkers()) {
            lost = this.anyValidBuild(player, worker);
            if (!lost) {
                break;
            }
        }
        return lost;
    }
}
