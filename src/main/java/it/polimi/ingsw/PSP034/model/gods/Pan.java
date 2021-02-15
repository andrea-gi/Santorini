package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

/**
 * Class representing Pan's rules and power.
 */
public class Pan extends GodsRules {
    private boolean hasBuilt = false;

    public Pan(IRules decoratedRules, Player player){
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
        boolean completed = super.executeState(currentPhase, worker, tile, choice);
        if (completed) {
            if (currentPhase == TurnPhase.START)
                hasBuilt = false;
            if (currentPhase == TurnPhase.BUILD)
                hasBuilt = true;
        }
        return completed;
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
        if(getPlayer().isOwner(worker)) {
            if (hasBuilt || !(getDefaultRules().checkWin(worker) ||
                    worker.getMyTile().getBuilding() + 2 <= super.getPreviousTile().getBuilding()))
                return false;
        }
        return super.checkWinRecursive(worker);
    }
}
