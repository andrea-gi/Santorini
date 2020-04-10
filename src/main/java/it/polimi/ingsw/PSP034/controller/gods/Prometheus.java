package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.controller.GodsRules;
import it.polimi.ingsw.PSP034.controller.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Prometheus extends GodsRules {
    private boolean usePower;

    public Prometheus(IRules decoratedRules, Player player){
        super(decoratedRules, player);
        usePower = false;
    }

    @Override
    public TurnPhase nextState(TurnPhase currentPhase){
        Worker myWorker = this.getPlayer().getWorker(super.getChosenSex());
        switch(currentPhase){
            case START:
                if(!checkBuildLost(this.getPlayer()))
                    return TurnPhase.POWER;
                else if (!checkMoveLost(this.getPlayer()))
                    return TurnPhase.MOVE;
                else
                    return TurnPhase.GAMEOVER;
            case POWER:
                if(usePower) {
                    super.setPreviousTile(myWorker.getMyTile()); // REWRITE PreviousTile, NO ACCIDENTAL WIN CONDITION
                    return TurnPhase.BUILD;
                }
                else
                    return TurnPhase.MOVE;
            case MOVE:
                usePower = false;  // in order to have a correct validBuild check
                if(getCompleteRules().checkWin(myWorker)){
                    return TurnPhase.WIN;
                }
                else if (anyValidBuild(myWorker)) {
                    return TurnPhase.BUILD;
                }
                else
                    return TurnPhase.GAMEOVER;
            case BUILD:
                if(getCompleteRules().checkWin(myWorker)){
                    return TurnPhase.WIN;
                }
                else if (usePower){
                    if (anyValidMove(myWorker))
                        return TurnPhase.MOVE;
                    else
                        return TurnPhase.GAMEOVER;
                }
                else
                    return TurnPhase.END;
        }
        return null;
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice){
        switch (currentPhase){
            case START:
                usePower = false;
                return true;
            case POWER:
                usePower = choice;
                return true;
            case MOVE:
                return super.executeState(TurnPhase.MOVE, worker, tile, choice);
            case BUILD:
                if(usePower){
                    getDefaultRules().setChosenSex(worker); // Save sex in order to have a reference to the Worker to be used throughout the turn
                }
                if(getCompleteRules().validBuild(worker, tile)){
                    build(tile);
                    return true;
                }
                else{
                    return false;
                }
            case END:
                return true;
        }
        return false;
    }



    @Override
    public boolean checkWin(Worker worker){
        return super.checkWin(worker);
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile){
        if(getPlayer().isOwner(worker)){
            if(!getDefaultRules().validMove(worker, destinationTile))
                return false;
            else if(usePower && worker.heightDifference(destinationTile) > 0  && worker.getSex() == getChosenSex())
                return false;
        }
        return validMoveRecursive(worker, destinationTile);
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile){
        return super.validBuild(worker, buildingTile);
    }
}
