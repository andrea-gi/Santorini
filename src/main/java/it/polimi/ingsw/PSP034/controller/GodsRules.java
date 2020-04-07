package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class GodsRules implements IRules, ITurnHandler {
    private final Player player;
    private static DefaultRules defaultRules;
    private static GodsRules completeRules;
    private IRules decoratedRules;

    public GodsRules(IRules decoratedRules, Player player){
        this.decoratedRules = decoratedRules;
        if (decoratedRules instanceof DefaultRules){ // Core decorated. Has default methods.
            defaultRules = (DefaultRules) decoratedRules;
        }
        completeRules = this;  // whole decorator the last addes
        this.player = player;
    }
    /*-------------------------------*/
    /*-----God specific methods------*/
    GodsRules getCompleteRules() {
        return completeRules;
    }

    Player getPlayer() {
        return player;
    }

    /*---------------------------*/
    /*-----Strategy methods------*/
    @Override
    public TurnPhase nextState(TurnPhase currentPhase){
        throw new NullPointerException("There is no default nextState strategy. Wrong method call.");
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice){
        throw new NullPointerException("There is no default executeState strategy. Wrong method call.");
    }

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
    }

    @Override
    public void build(Tile buildTile){
        decoratedRules.build(buildTile);
    }


    @Override
    public boolean validMove(Worker worker, Tile destinationTile){
        if ( decoratedRules instanceof DefaultRules ) {
            return true;
        }
        else{
            return (decoratedRules.validMove(worker, destinationTile));
        }
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile){
        if ( !(decoratedRules instanceof DefaultRules) ) {
            return true;
        }
        else{
            return (decoratedRules.validBuild(worker, buildingTile));
        }
    }

    @Override
    public boolean checkWin(Worker worker){
        return defaultRules.checkWin(worker);
    }

    @Override
    public boolean anyValidMove(Worker worker) {
        boolean anyMove = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validMove(worker, neighbour); //ATTENZIONE, REGOLE COMPLETE; USARE QUESTA!
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
            lost = this.anyValidMove(worker);
            if (!lost) {
                break;
            }
        }
        return lost;
    }

    @Override
    public boolean anyValidBuild(Worker worker) {
        boolean anyBuild = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validBuild(worker, neighbour); //ATTENZIONE, REGOLE COMPLETE; USARE QUESTA!!
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
            lost = this.anyValidBuild(worker);
            if (!lost) {
                break;
            }
        }
        return lost;
    }
}
