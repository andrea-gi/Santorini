package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.*;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
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
                return super.nextState(TurnPhase.START);
            case MOVE:
                return super.nextState(TurnPhase.MOVE);
            case BUILD:
                if(getCompleteRules().checkWin(this.getPlayer().getWorker(super.getChosenSex()))) {
                    return TurnPhase.WIN;
                }else {
                    if (usePower) {
                        return TurnPhase.END;
                    } else {
                        usePower = true; // in order to have a correct validBuild check
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
                return super.executeState(TurnPhase.MOVE, worker, tile, choice);
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
