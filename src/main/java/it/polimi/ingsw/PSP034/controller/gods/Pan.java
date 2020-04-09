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
        switch (currentPhase){
            case START:
                if (!checkMoveLost(this.getPlayer())) {
                    return TurnPhase.MOVE;
                }else{
                    return TurnPhase.GAMEOVER;
                }
            case MOVE:
                if(checkWin(this.getPlayer().getWorker(super.getChosenSex()))) {
                    return TurnPhase.WIN;
                }else {
                    if (super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                        return TurnPhase.BUILD;
                    else
                        return TurnPhase.GAMEOVER;
                }
            case BUILD:
                if(checkWin(this.getPlayer().getWorker(super.getChosenSex()))) {
                    return TurnPhase.WIN;
                }else {
                    return TurnPhase.END;
                }
        }
        return null;
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        switch (currentPhase){
            case START:
                return true;
            case MOVE:
                if(super.getCompleteRules().validMove(worker, tile)){
                    super.move(worker, tile);
                    return true;
                } else
                    return false;
            case BUILD:
                if(super.getCompleteRules().validBuild(worker, tile)){
                    super.build(tile);
                    return true;
                }else
                    return false;
            case END:
                return true;
        }
        return false;
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
