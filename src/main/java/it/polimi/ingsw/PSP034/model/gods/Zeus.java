package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.PlayPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;


public class Zeus extends GodsRules {
    private boolean builtUnderMe = false;

    public Zeus(IRules decoratedRules, Player player){
        super(decoratedRules, player);
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public NextStateInfo nextState(TurnPhase currentPhase) {
        return super.nextState(currentPhase);
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice) {
        if(currentPhase == TurnPhase.START){
                builtUnderMe = false;
                return super.executeState(TurnPhase.START, worker, tile, choice);
        }
        else
            return super.executeState(currentPhase, worker, tile, choice);
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {
        return super.validMove(worker, destinationTile);
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
        builtUnderMe = false;
        if(getPlayer().isOwner(worker)){
            if (!buildingTile.equals(worker.getMyTile())){
                if(!super.getDefaultRules().validBuild(worker, buildingTile)){
                    return false;
                }
            }else if (worker.getMyTile().getBuilding() == Constant.LEVEL_THREE){
                return false;
            }
            else
                builtUnderMe = true;
        }
        return validBuildRecursive(worker, buildingTile);
    }

    @Override
    public boolean checkWin(Worker worker){
        if(getPlayer().isOwner(worker)) {
            if (builtUnderMe)
                return false;
            else if (!(getDefaultRules().checkWin(worker)))
                return false;
        }
        return super.checkWinRecursive(worker);
    }
}