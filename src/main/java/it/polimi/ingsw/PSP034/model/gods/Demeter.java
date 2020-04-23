package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.playPhase.RequiredActions;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
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
    public NextStateInfo nextState(TurnPhase currentPhase) {
        switch (currentPhase) {
            case START:
                return super.nextState(TurnPhase.START);
            case MOVE:
                return super.nextState(TurnPhase.MOVE);
            case BUILD:
                if (checkWin(getPlayer().getWorker(getChosenSex())))
                    return new NextStateInfo(TurnPhase.WIN);
                if (usePower)
                    return new NextStateInfo(TurnPhase.END);
                else {
                    if (anyValidBuild(getPlayer().getWorker(getChosenSex())))
                        return new NextStateInfo(TurnPhase.POWER, RequiredActions.REQUEST_POWER);
                    else
                        return new NextStateInfo(TurnPhase.END);
                }
            case POWER:
                if (usePower)
                    return new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_BUILD);
                else
                    return new NextStateInfo(TurnPhase.END);
            case END:
                return null;
        }
        return null;
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice) {
        boolean executed = false;
        switch(currentPhase){
            case START:
                usePower = false;
                return super.executeState(TurnPhase.START, worker, tile, choice);
            case MOVE:
                return super.executeState(TurnPhase.MOVE, worker, tile, choice);
            case BUILD:
                if(super.executeState(TurnPhase.BUILD, worker, tile, choice)){
                    previousBuilding = tile;
                    executed = true;
                    break;
                }
                executed = false;
                break;
            case POWER:
                usePower = choice;
                executed = true;
                break;
            case END:
                executed = true;
                break;
        }
        return executed;
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
