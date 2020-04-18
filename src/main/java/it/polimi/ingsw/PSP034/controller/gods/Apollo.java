package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.controller.GodsRules;
import it.polimi.ingsw.PSP034.controller.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Apollo extends GodsRules {

    public Apollo(IRules decoratedRules, Player player){
        super(decoratedRules, player);
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public TurnPhase nextState(TurnPhase currentPhase) {
        return super.nextState(currentPhase);
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        if (currentPhase == TurnPhase.MOVE){
            if(getCompleteRules().validMove(worker, tile)) {
                this.move(worker, tile);
                return true;
            }else
                return false;
        }

        return super.executeState(currentPhase, worker, tile, choice);
    }

    @Override
    public void move(Worker worker, Tile destinationTile) {
        Worker opponentWorker = destinationTile.getWorker();
        super.move(worker, destinationTile);
        if(opponentWorker != null){
            opponentWorker.setMyTile(getPreviousTile());
            getPreviousTile().setWorker(opponentWorker);
        }
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {
        if(getPlayer().isOwner(worker)){
            if(!getDefaultRules().validMove(worker, destinationTile))
                if(!validForApollo(worker, destinationTile))
                    return false;
                else
                    return super.validMoveRecursive(worker, destinationTile);
            else
                return super.validMoveRecursive(worker, destinationTile);
        }else
            return super.validMoveRecursive(worker, destinationTile);
    }

    private boolean validForApollo(Worker worker, Tile destinationTile){
        if(destinationTile.getWorker() != null){
            if (getPlayer().isOwner(destinationTile.getWorker()))
                return false;
            if (!(worker.heightDifference(destinationTile) <= 1))
                return false;
            return worker.getMyTile().isNeighbouringTile(destinationTile);
        }
        return false;
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
        return super.validBuild(worker, buildingTile);
    }

    @Override
    public boolean checkWin(Worker worker) {
        return super.checkWin(worker);
    }
}