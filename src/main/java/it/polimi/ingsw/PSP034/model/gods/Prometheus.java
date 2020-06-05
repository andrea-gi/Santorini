package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.playPhase.RequiredActions;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
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
    public NextStateInfo nextState(TurnPhase currentPhase){
        Worker myWorker = this.getPlayer().getWorker(super.getChosenSex());
        switch(currentPhase){
            case START:
                if(!checkBuildLost(this.getPlayer()))
                    return new NextStateInfo(TurnPhase.POWER, RequiredActions.REQUEST_POWER);
                else if (!checkMoveLost(this.getPlayer())) {
                    usePower = false;
                    return new NextStateInfo(TurnPhase.MOVE, RequiredActions.REQUEST_WORKER, RequiredActions.REQUEST_MOVE);
                }
                else
                    return new NextStateInfo(TurnPhase.GAMEOVER);
            case POWER:
                if(usePower) {
                    return new NextStateInfo(TurnPhase.BUILD, RequiredActions.REQUEST_WORKER, RequiredActions.REQUEST_BUILD);
                }
                else
                    return new NextStateInfo(TurnPhase.MOVE, RequiredActions.REQUEST_WORKER, RequiredActions.REQUEST_MOVE);
            case MOVE:
                usePower = false;  // in order to have a correct validBuild check
                if(getCompleteRules().checkWin(myWorker)){
                    return new NextStateInfo(TurnPhase.WIN);
                }
                else if (anyValidBuild(myWorker)) {
                    return new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_BUILD);
                }
                else
                    return new NextStateInfo(TurnPhase.GAMEOVER);
            case BUILD:
                if(getCompleteRules().checkWin(myWorker)){
                    return new NextStateInfo(TurnPhase.WIN);
                }
                else if (usePower){
                    if (anyValidMove(myWorker))
                        return new NextStateInfo(TurnPhase.MOVE, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_MOVE);
                    else
                        return new NextStateInfo(TurnPhase.GAMEOVER);
                }
                else
                    return new NextStateInfo(TurnPhase.END);
        }
        return null;
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice){
        boolean executed = false;
        switch (currentPhase){
            case START:
                usePower = true; // To have a correct validBuild (and checkBuildLost)
                executed = true;
                break;
            case POWER:
                usePower = choice;
                executed = true;
                break;
            case MOVE:
                return super.executeState(TurnPhase.MOVE, worker, tile, choice);
            case BUILD:
                if(usePower){
                    getDefaultRules().setChosenSex(worker); // Save sex in order to have a reference to the Worker to be used throughout the turn
                    super.setPreviousTile(worker.getMyTile()); // REWRITE PreviousTile, NO ACCIDENTAL WIN CONDITION
                }
                if(getCompleteRules().validBuild(worker, tile)){
                    build(tile);
                    executed = true;
                    break;
                }
                else{
                    executed = false;
                    break;
                }
            case END:
                executed = true;
                break;
        }
        getDefaultRules().modelUpdated();
        return executed;
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
            else if(usePower && (worker.heightDifference(destinationTile) > 0  || worker.getSex() != getChosenSex()))
                return false;
        }
        return validMoveRecursive(worker, destinationTile);
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile){
        if(usePower){
            //Checks if tile is unoccupied
            if (buildingTile.getWorker() != null)
                return false;

            //Checks if tile does not have already a dome
            if (buildingTile.hasDome())
                return false;

            //Checks if the tile is different
            if(worker.getMyTile().getX() == buildingTile.getX()
                    && worker.getMyTile().getY() == buildingTile.getY())
                return false;

            //Checks if tiles are neighbour
            if (!worker.getMyTile().isNeighbouringTile(buildingTile))
                return false;

            return super.validBuildRecursive(worker, buildingTile);
        }
        else {
            return super.validBuild(worker, buildingTile);
        }
    }
}
