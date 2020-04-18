package it.polimi.ingsw.PSP034.model.gods;


import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.RequiredActions;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

public class Artemis extends GodsRules {
    private boolean usePower;
    private boolean secondMove;

    public Artemis(IRules decoratedRules, Player player){
        super(decoratedRules, player);
        usePower = false;
        secondMove = false;
    }

    @Override
    protected Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public NextStateInfo nextState(TurnPhase currentPhase) {
        switch (currentPhase){
            case START:
                return super.nextState(TurnPhase.START);
            case MOVE:
                if(getCompleteRules().checkWin(this.getPlayer().getWorker(super.getChosenSex()))) {
                    return new NextStateInfo(TurnPhase.WIN);
                }else {
                    if (secondMove) {
                        if (super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                            return new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_BUILD);
                        else
                            return new NextStateInfo(TurnPhase.GAMEOVER);
                    } else {
                        usePower = true; // in order to have a correct validBuild check
                        if (super.anyValidMove(this.getPlayer().getWorker(super.getChosenSex())))
                            return new NextStateInfo(TurnPhase.POWER, RequiredActions.REQUEST_POWER);
                        else if (super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                            return new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_BUILD);
                        else
                            return new NextStateInfo(TurnPhase.GAMEOVER);
                    }
                }
            case POWER:
                if(usePower)
                    return new NextStateInfo(TurnPhase.MOVE, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_MOVE); //the existence of a possible move has already been tested
                else{
                    if(super.anyValidBuild(this.getPlayer().getWorker(super.getChosenSex())))
                        return new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_BUILD);
                    else
                        return new NextStateInfo(TurnPhase.GAMEOVER);
                }
            case BUILD:
                return super.nextState(TurnPhase.BUILD);
        }
        return null;
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, Boolean choice) {
        boolean executed = false;
        switch (currentPhase){
            case START:
                usePower = false;
                secondMove = false;
                executed = true;
                break;
            case MOVE:
                if(super.getCompleteRules().validMove(worker, tile)){
                    super.move(worker, tile);
                    secondMove = true;
                    executed = true;
                } else {
                    executed = false;
                }
                break;
            case BUILD:
                return super.executeState(TurnPhase.BUILD, worker, tile, choice);
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
        if(getPlayer().isOwner(worker)){
            if(!super.getDefaultRules().validMove(worker, destinationTile)){
                return false;
            }else if(usePower  &&  destinationTile.equals(super.getPreviousTile())  &&  worker.getSex() != super.getChosenSex()){
                return false;
            }
        }
        return validMoveRecursive(worker, destinationTile);
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
