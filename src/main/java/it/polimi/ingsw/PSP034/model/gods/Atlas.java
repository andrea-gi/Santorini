package it.polimi.ingsw.PSP034.model.gods;

import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.playPhase.RequiredActions;
import it.polimi.ingsw.PSP034.model.GodsRules;
import it.polimi.ingsw.PSP034.model.IRules;
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
    public NextStateInfo nextState(TurnPhase currentPhase) {
        switch(currentPhase){
            case START:
                return super.nextState(TurnPhase.START);
            case MOVE:
                if(checkWin(getPlayer().getWorker(getChosenSex())))
                    return new NextStateInfo(TurnPhase.WIN);
                if(anyValidBuild(getPlayer().getWorker(getChosenSex())))
                    return new NextStateInfo(TurnPhase.POWER, RequiredActions.REQUEST_POWER);
                else
                    return new NextStateInfo(TurnPhase.GAMEOVER);
            case POWER:
                return new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_BUILD);
            case BUILD:
                return super.nextState(TurnPhase.BUILD);
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
            case POWER:
                usePower = choice;
                executed = true;
                break;
            case BUILD:
                if(getCompleteRules().validBuild(worker, tile)) {
                    if (usePower) {
                        buildDome(tile);
                    } else {
                        super.build(tile);
                    }
                    executed = true;
                }else{
                    executed = false;
                }
                break;
            case END:
                executed = true;
                break;
        }
        getDefaultRules().modelUpdated();
        return executed;
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
