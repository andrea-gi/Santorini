package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.NextStateInfo;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;
import it.polimi.ingsw.PSP034.constants.*;

public class Limus extends GodsRules {
    public Limus(IRules decoratedRules, Player player){
        super(decoratedRules, player);
    }

    @Override
    public NextStateInfo nextState(TurnPhase currentPhase) {
        return super.nextState(currentPhase);
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        return super.executeState(currentPhase, worker, tile, choice);
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {
        return super.validMove(worker, destinationTile);
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
        if(getPlayer().isOwner(worker)){
            return super.validBuild(worker, buildingTile);
        }else{
            if(buildingTile.getBuilding() <= Constant.LEVEL_TWO) {
                if (buildingTile.isNeighbouringTile(getPlayer().getWorker(Sex.MALE).getMyTile()))
                    return false;
                if (buildingTile.isNeighbouringTile(getPlayer().getWorker(Sex.FEMALE).getMyTile()))
                    return false;
            }
            return validBuildRecursive(worker, buildingTile);
        }
    }
}
