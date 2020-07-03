package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

/**
 * Class representing Minotaur's rules and power.
 */
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
    public NextStateInfo nextState(TurnPhase currentPhase){
        return super.nextState(currentPhase);
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice) {
        boolean executed = false;
        switch (currentPhase){
            case START:
                movingOtherWorker = false;
                executed = true;
                break;
            case MOVE:
                if(getCompleteRules().validMove(worker, tile)){
                    if(movingOtherWorker) {
                        move(tile.getWorker(), worker.getMyTile().getNextTileSameDirection(tile));
                    }
                    move(worker, tile);
                    executed = true;
                    break;
                } else {
                    executed = false;
                    break;
                }
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
    public boolean validMove(Worker worker, Tile destinationTile){
        movingOtherWorker = false;
        if(getPlayer().isOwner(worker)) {
            //Checks if the tile is different
            if(worker.getMyTile().getX() == destinationTile.getX()
                    && worker.getMyTile().getY() == destinationTile.getY())
                return false;

            //Checks valid move up (at most 1 level)
            if (worker.heightDifference(destinationTile) > 1)
                return false;

            //Checks if tile does not have a dome
            if (destinationTile.hasDome())
                return false;

            //Checks if tiles are neighbour
            if (!worker.getMyTile().isNeighbouringTile(destinationTile))
                return false;

            if (destinationTile.getWorker() != null) {
                if (getPlayer().isOwner(destinationTile.getWorker()))
                    return false;
                else if (!worker.getMyTile().existsTileSameDirection(destinationTile))
                    return false;
                else if (!validOtherWorkerMove(worker, destinationTile))
                    return false;
            }
        }
        return validMoveRecursive(worker, destinationTile);
    }


    @Override
    public boolean validBuild(Worker worker, Tile buildingTile){
        return super.validBuild(worker, buildingTile);
    }

    /**
     * Checks if you can use the Minotaur power (pushing other worker). Toggles a boolean which states the activation of the God power.
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
