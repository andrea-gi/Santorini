package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

/**
 * Class representing Apollo's rules and power.
 */
public class Apollo extends GodsRules {

    public Apollo(IRules decoratedRules, Player player){
        super(decoratedRules, player);
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public NextStateInfo nextState(TurnPhase currentPhase) {
        return super.nextState(currentPhase);
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice) {
        boolean executed;
        if (currentPhase == TurnPhase.MOVE){
            if(getCompleteRules().validMove(worker, tile)) {
                this.move(worker, tile);
                executed = true;
            }else
                executed = false;
            getDefaultRules().modelUpdated();
            return executed;
        }
        return super.executeState(currentPhase, worker, tile, choice);
    }

    @Override
    public void move(Worker worker, Tile destinationTile) {
        Worker opponentWorker = destinationTile.getWorker();
        super.move(worker, destinationTile);
        if(opponentWorker != null){
            opponentWorker.setMyTile(getPreviousTile());
            getPreviousTile().setWorker(opponentWorker);
        }
    }

    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {
        if(getPlayer().isOwner(worker)){
            if(!getDefaultRules().validMove(worker, destinationTile))
                if(!validForApollo(worker, destinationTile))
                    return false;
                else
                    return super.validMoveRecursive(worker, destinationTile);
            else
                return super.validMoveRecursive(worker, destinationTile);
        }else
            return super.validMoveRecursive(worker, destinationTile);
    }

    private boolean validForApollo(Worker worker, Tile destinationTile){
        if(destinationTile.getWorker() != null){
            if(worker.getMyTile().getX() == destinationTile.getX()
                    && worker.getMyTile().getY() == destinationTile.getY())
                return false;
            if (getPlayer().isOwner(destinationTile.getWorker()))
                return false;
            if (!(worker.heightDifference(destinationTile) <= 1))
                return false;
            return worker.getMyTile().isNeighbouringTile(destinationTile);
        }
        return false;
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
        return super.validBuild(worker, buildingTile);
    }

    @Override
    public boolean checkWin(Worker worker) {
        return super.checkWin(worker);
    }
}