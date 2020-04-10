package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.controller.GodsRules;
import it.polimi.ingsw.PSP034.controller.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Atlas extends GodsRules {
    private boolean usePower;

    public Atlas(IRules decoratedRules, Player player){
        super(decoratedRules, player);
        usePower = false;
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public TurnPhase nextState(TurnPhase currentPhase) {
        switch(currentPhase){
            case START:
                return super.nextState(TurnPhase.START);
            case MOVE:
                //TODO -- CHECK
                return TurnPhase.POWER;
            case POWER:
                return TurnPhase.BUILD;
            case BUILD:
                return super.nextState(TurnPhase.BUILD);
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
            case POWER:
                usePower = choice;
                return true;
            case BUILD:
                if(getCompleteRules().validBuild(worker, tile)) {
                    if (usePower) {
                        buildDome(tile);
                        return true;
                    } else {
                        super.build(tile);
                        return true;
                    }
                }
                return false;
            case END:
                return true;
        }
        return null;
    }

    private void buildDome(Tile buildingTile){
        buildingTile.setDome(true);
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
    public boolean checkWin(Worker worker) {
        return super.checkWin(worker);
    }
}
