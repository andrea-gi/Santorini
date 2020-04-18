package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.NextStateInfo;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Athena extends GodsRules {
    private boolean movedUp;

    public Athena(IRules decoratedRules, Player player){
        super(decoratedRules, player);
        movedUp = false;
    }

    @Override
    protected Player getPlayer(){
        return super.getPlayer();
    }

    @Override
    public NextStateInfo nextState(TurnPhase currentPhase) {
        return super.nextState(currentPhase);
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        switch (currentPhase){
            case START:
                movedUp = false;
                return true;
            case MOVE:
                super.move(worker, tile);
                if(worker.heightDifference(super.getPreviousTile()) == -1)
                    movedUp = true;
                return true;
            case BUILD:
                super.build(tile);
                return true;
            case END:
                return true;
        }
        return false;
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {
        if(getPlayer().isOwner(worker)){
            return super.validMove(worker, destinationTile);
        }else{
            if(movedUp  &&  (worker.heightDifference(destinationTile) >= 1))
                return false;
            else
                return super.validMoveRecursive(worker, destinationTile);
        }
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
        return super.validBuild(worker, buildingTile);
    }

    @Override
    public boolean checkWin(Worker worker){
        return super.checkWin(worker);
    }
}
