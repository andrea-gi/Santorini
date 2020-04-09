package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.controller.GodsRules;
import it.polimi.ingsw.PSP034.controller.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Minotaur extends GodsRules {
    private boolean movingOtherWorker;

    public Minotaur(IRules decoratedRules, Player player){
        super(decoratedRules, player);
        movingOtherWorker = false;
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }


    @Override
    public TurnPhase nextState(TurnPhase currentPhase){
        return super.nextState(currentPhase);
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        switch (currentPhase){
            case START:
                return true;
            case MOVE:
                if(getCompleteRules().validMove(worker, tile)){
                    if(movingOtherWorker) {
                        move(tile.getWorker(), worker.getMyTile().getNextTileSameDirection(tile));
                    }
                    move(worker, tile);
                    return true;
                } else
                    return false;
            case BUILD:
                if(getCompleteRules().validBuild(worker, tile)){
                    build(tile);
                    return true;
                }else
                    return false;
            case END:
                return true;
        }
        return false;
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile){
        if(getPlayer().isOwner(worker)) {
            //Checks valid move up (at most 1 level)
            if (worker.heightDifference(destinationTile) > 1)
                return false;

            //Checks if tile does not have a dome
            if (destinationTile.hasDome())
                return false;

            //Checks if tiles are neighbour
            if (worker.getMyTile().isNeighbouringTile(destinationTile))
                return false;

            if (destinationTile.getWorker() != null) {
                if (getPlayer().isOwner(destinationTile.getWorker()))
                    return false;
                else if (!worker.getMyTile().existsTileSameDirection(destinationTile))
                    return false;
                else {
                    return validOtherWorkerMove(worker, destinationTile);
                }
            } else {
                return true;
            }
        }
        return validMoveRecursive(worker, destinationTile);
    }


    @Override
    public boolean validBuild(Worker worker, Tile buildingTile){
        return super.validBuild(worker, buildingTile);
    }

    /**
     * Checks if you can use the Minotaur power (pushing other worker)
     * @param worker Reference to the worker that is using the power (and needs to push the opponent's worker)
     * @param passingTile Reference to the neighbouring tile where an opponent's worker is placed.
     * @return true if the opponent's worker can be pushed
     */
    private boolean validOtherWorkerMove(Worker worker, Tile passingTile){
        Tile destinationTile = worker.getMyTile().getNextTileSameDirection(passingTile);
        if( !destinationTile.hasDome() && destinationTile.getWorker() == null ){
            movingOtherWorker = true;
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean checkWin(Worker worker){
        return super.checkWin(worker);
    }
}
