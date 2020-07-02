package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
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
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice) {
        boolean executed = false;
        switch (currentPhase){
            case START:
                movedUp = false;
                executed = true;
                break;
            case MOVE:
                if (super.getCompleteRules().validMove(worker, tile)) {
                    super.move(worker, tile);
                    if (worker.heightDifference(super.getPreviousTile()) == -1)
                        movedUp = true;
                    executed = true;
                } else {
                    executed = false;
                }
                break;
            case BUILD:
                return super.executeState(TurnPhase.BUILD, worker, tile, choice);
            case END:
                executed = true;
                break;
        }
        getDefaultRules().modelUpdated();
        return executed;
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
