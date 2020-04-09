package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.controller.*;
import it.polimi.ingsw.PSP034.model.*;


public class Pan extends GodsRules{
    public Pan(IRules decoratedRules, Player player){
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
        return super.executeState(currentPhase, worker, tile, choice);
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {
        return super.validMove(worker, destinationTile);
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
        return super.validBuild(worker, buildingTile);
    }

    @Override
    public boolean checkWin(Worker worker){
        return (super.checkWin(worker) || worker.getMyTile().getBuilding() + 2 <= super.getPreviousTile().getBuilding());
    }
}
