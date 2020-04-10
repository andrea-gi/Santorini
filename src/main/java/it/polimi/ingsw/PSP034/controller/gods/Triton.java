package it.polimi.ingsw.PSP034.controller.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.controller.GodsRules;
import it.polimi.ingsw.PSP034.controller.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;


public class Triton extends GodsRules {
    private boolean usePower;

    public Triton(IRules decoratedRules, Player player){
        super(decoratedRules, player);
        usePower = false;
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
                if(getCompleteRules().checkWin(this.getPlayer().getWorker(super.getChosenSex()))) {
                    return TurnPhase.WIN;
                }else {
                    if (getPlayer().getWorker(getChosenSex()).getMyTile().isPerimeter() && super.anyValidMove(this.getPlayer().getWorker(super.getChosenSex()))){
                        return TurnPhase.POWER;
                    }
                    else if (super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                        return TurnPhase.BUILD;
                    else
                        return TurnPhase.GAMEOVER;
                }
            case POWER:
                if(usePower)
                    return TurnPhase.MOVE; //the existence of a possible move has already been tested
                else{
                    if(super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                        return TurnPhase.BUILD;
                    else
                        return TurnPhase.GAMEOVER;
                }
            case BUILD:
                return super.nextState(TurnPhase.BUILD);
        }
        return null;
    }

    @Override
    public Boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        switch (currentPhase){
            case START:
                usePower = false;
                return true;
            case MOVE:
                return super.executeState(TurnPhase.MOVE, worker, tile, choice);
            case BUILD:
                return super.executeState(TurnPhase.BUILD, worker, tile, choice);
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
        return super.validBuild(worker, buildingTile);
    }

    @Override
    public boolean checkWin(Worker worker){
        return super.checkWin(worker);
    }
}
