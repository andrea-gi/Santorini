package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.controller.GodsRules;
import it.polimi.ingsw.PSP034.controller.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Ephaestus extends GodsRules {
    private boolean usePower;
    private Tile myFirstBuilding;

    public Ephaestus(IRules decoratedRules, Player player){
        super(decoratedRules, player);
        usePower = false;
        myFirstBuilding = null;
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
                if(getCompleteRules().checkWin(this.getPlayer().getWorker(super.getChosenSex()))) {
                    return TurnPhase.WIN;
                }else {
                        if (super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                            return TurnPhase.BUILD;
                        else
                            return TurnPhase.GAMEOVER;
                    }
            case BUILD:
                if(getCompleteRules().checkWin(this.getPlayer().getWorker(super.getChosenSex()))) {
                    return TurnPhase.WIN;
                }else {
                    if (usePower) {
                        return TurnPhase.END;
                    } else {
                        if (super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                            return TurnPhase.POWER;
                        else
                            return TurnPhase.END;
                    }
                }
            case POWER:
                if(usePower) {
                    return TurnPhase.BUILD;//the existence of a possible build has already been tested
                }
                else{
                    return TurnPhase.END;
                }
        }
        return null;
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        switch (currentPhase){
            case START:
                usePower = false;
                myFirstBuilding = null;
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
                    myFirstBuilding = tile;
                    return true;
                }else
                    return false;
            case POWER:
                usePower = choice;
                return true;
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
        if(getPlayer().isOwner(worker)){
            if(!super.getDefaultRules().validBuild(worker, buildingTile)){
                return false;
            }else if(usePower  &&  !buildingTile.equals(myFirstBuilding) && buildingTile.getBuilding() == Constant.LEVEL_THREE  &&  worker.getSex() != super.getChosenSex()){
                return false;
            }
        }
        return validBuildRecursive(worker, buildingTile);
    }

    @Override
    public boolean checkWin(Worker worker){ return super.checkWin(worker);}
}
