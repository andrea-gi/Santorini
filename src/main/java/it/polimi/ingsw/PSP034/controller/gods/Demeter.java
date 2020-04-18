package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.controller.GodsRules;
import it.polimi.ingsw.PSP034.controller.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Demeter extends GodsRules {
    private boolean usePower;
    private Tile previousBuilding;

    public Demeter(IRules decoratedRules, Player player) {
        super(decoratedRules, player);
        usePower = false;
        previousBuilding = null;
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public TurnPhase nextState(TurnPhase currentPhase) {
        switch (currentPhase) {
            case START:
                return super.nextState(TurnPhase.START);
            case MOVE:
                return super.nextState(TurnPhase.MOVE);
            case BUILD:
                if (checkWin(getPlayer().getWorker(getChosenSex())))
                    return TurnPhase.WIN;
                if (usePower)
                    return TurnPhase.END;
                else {
                    if (anyValidBuild(getPlayer().getWorker(getChosenSex())))
                        return TurnPhase.POWER;
                    else
                        return TurnPhase.END;
                }
            case POWER:
                if (usePower)
                    return TurnPhase.BUILD;
                else
                    return TurnPhase.END;
            case END:
                return null;
        }
        return null;
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        switch(currentPhase){
            case START:
                usePower = false;
                return super.executeState(TurnPhase.START, worker, tile, choice);
            case MOVE:
                return super.executeState(TurnPhase.MOVE, worker, tile, choice);
            case BUILD:
                if(super.executeState(TurnPhase.BUILD, worker, tile, choice)){
                    previousBuilding = tile;
                    return true;
                }
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
            }else if(usePower  &&  (getChosenSex() != worker.getSex()  ||  buildingTile == previousBuilding)){
                return false;
            }
        }
        return validBuildRecursive(worker, buildingTile);
    }
}
