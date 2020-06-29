package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.constants.TurnPhase;
import it.polimi.ingsw.PSP034.messages.playPhase.NextStateInfo;
import it.polimi.ingsw.PSP034.messages.playPhase.RequiredActions;

import java.util.ArrayList;

/**
 * Contains the god specific rules, decorating the main core: {@link DefaultRules}.
 */
public class GodsRules implements IRules, IStateManager {
    private final Player player;
    private static DefaultRules defaultRules;
    private static GodsRules completeRules;
    private IRules decoratedRules;

    public GodsRules(IRules decoratedRules, Player player){
        this.decoratedRules = decoratedRules;
        if (decoratedRules instanceof DefaultRules){ // Core decorated. Has default methods.
            defaultRules = (DefaultRules) decoratedRules;
        }
        completeRules = this;  // whole decorator is the latest
        this.player = player;
    }
    /*-------------------------------*/
    /*-----God specific methods------*/

    protected static void setCompleteRules(GodsRules completeRules) {
        GodsRules.completeRules = completeRules;
    }

    protected void setDecoratedRules(IRules decoratedRules) {
        this.decoratedRules = decoratedRules;
    }

    protected GodsRules getCompleteRules() {
        return completeRules;
    }

    protected IRules getDecoratedRules(){
        return decoratedRules;
    }

    protected Player getPlayer() {
        return player;
    }

    /*---------------------------*/
    /*-----Strategy methods------*/
    @Override
    public NextStateInfo nextState(TurnPhase currentPhase) {
        switch (currentPhase){
            case START:
                if (!checkMoveLost(player)) {
                    return new NextStateInfo(TurnPhase.MOVE, RequiredActions.REQUEST_WORKER, RequiredActions.REQUEST_MOVE);
                }else{
                    return new NextStateInfo(TurnPhase.GAMEOVER);
                }
            case MOVE:
                if(completeRules.checkWin(player.getWorker(getChosenSex()))) {
                    return new NextStateInfo(TurnPhase.WIN);
                }else {
                    if (anyValidBuild(player.getWorker(getChosenSex())))
                        return new NextStateInfo(TurnPhase.BUILD, RequiredActions.getRequiredSex(getChosenSex()), RequiredActions.REQUEST_BUILD);
                    else
                        return new NextStateInfo(TurnPhase.GAMEOVER);
                }
            case BUILD:
                if(completeRules.checkWin(player.getWorker(getChosenSex()))) {
                    return new NextStateInfo(TurnPhase.WIN);
                }else {
                    return new NextStateInfo(TurnPhase.END);
                }
        }
        return null;
    }

    @Override
    public boolean executeState(TurnPhase currentPhase, Worker worker, Tile tile, boolean choice) {
        boolean executed = false;
        switch (currentPhase){
            case START:
                executed = true;
                break;
            case MOVE:
                if(completeRules.validMove(worker, tile)){
                    move(worker, tile);
                    executed = true;
                } else
                    executed = false;
                break;
            case BUILD:
                if(completeRules.validBuild(worker, tile)){
                    build(tile);
                    executed = true;
                }else
                    executed = false;
                break;
        }
        defaultRules.modelUpdated();
        // TODO -- chiamare notify delle DefaultRules che chiama quella del Game
        return executed;
    }

    /*---------------------------*/
    /*-----Decorator methods-----*/
    @Override
    public DefaultRules getDefaultRules() {
        return defaultRules;
    }

    @Override
    public Sex getChosenSex() {
        return defaultRules.getChosenSex();
    }

    @Override
    public void setChosenSex(Worker chosenWorker) {
        defaultRules.setChosenSex(chosenWorker);
    }

    @Override
    public Tile getPreviousTile(){ return defaultRules.getPreviousTile();}

    @Override
    public void setPreviousTile(Tile tile){ defaultRules.setPreviousTile(tile);}


    @Override
    public void move(Worker worker, Tile destinationTile){
        defaultRules.move(worker, destinationTile);
    }

    @Override
    public void build(Tile buildTile){
        defaultRules.build(buildTile);
    }


    @Override
    public boolean validMove(Worker worker, Tile destinationTile){
        if (player.isOwner(worker) && !defaultRules.validMove(worker, destinationTile)){
            return false;
        }
        else {
            return validMoveRecursive(worker, destinationTile);
        }
    }

    protected final boolean validMoveRecursive(Worker worker, Tile destinationTile){
        if (decoratedRules instanceof DefaultRules) {
            return true;
        } else {
            return (decoratedRules.validMove(worker, destinationTile));
        }
    }

    @Override
    public boolean validBuild(Worker worker, Tile buildingTile){
        if ( player.isOwner(worker) && !defaultRules.validBuild(worker, buildingTile) ){
            return false;
        }else {
            return validBuildRecursive(worker, buildingTile);
        }
    }

    protected final boolean validBuildRecursive(Worker worker, Tile buildingTile){
        if (decoratedRules instanceof DefaultRules) {
            return true;
        } else {
            return (decoratedRules.validBuild(worker, buildingTile));
        }
    }

    @Override
    public boolean checkWin(Worker worker){
        if(player.isOwner(worker) && !defaultRules.checkWin(worker)){
            return false;
        }
        else {
            return checkWinRecursive(worker);
        }
    }

    protected final boolean checkWinRecursive(Worker worker) {
        if (decoratedRules instanceof DefaultRules)
            return true;
        else
            return decoratedRules.checkWin(worker);
    }

    @Override
    public final boolean anyValidMove(Worker worker) {
        boolean anyMove = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validMove(worker, neighbour); //ATTENZIONE, REGOLE COMPLETE; USARE QUESTA!
            if (valid) {
                anyMove = true;
                break;
            }
        }
        return anyMove;
    }

    public static ArrayList<Tile> availableMovingTiles(Worker worker){
        ArrayList<Tile> resultTiles = new ArrayList<>();
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validMove(worker, neighbour);
            if (valid) {
                resultTiles.add(neighbour);
            }
        }
        return resultTiles;
    }

    @Override
    public final boolean checkMoveLost(Player player) {
        boolean keepPlaying = false;
        for (Worker worker :
                player.getMyWorkers()) {
            keepPlaying = this.anyValidMove(worker);
            if (keepPlaying) {
                break;
            }
        }
        return !keepPlaying;
    }

    @Override
    public final boolean anyValidBuild(Worker worker) {
        boolean anyBuild = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validBuild(worker, neighbour); // Complete rules!
            if (valid) {
                anyBuild = true;
                break;
            }
        }
        return anyBuild;
    }

    public static ArrayList<Tile> availableBuildingTiles(Worker worker){
        ArrayList<Tile> resultTiles = new ArrayList<>();
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            boolean valid = completeRules.validBuild(worker, neighbour);
            if (valid) {
                resultTiles.add(neighbour);
            }
        }
        return resultTiles;
    }

    @Override
    public final boolean checkBuildLost(Player player) {
        boolean keepPlaying = false;
        Sex firstSex = Sex.MALE; // Checks both Workers, used only with REQUEST_WORKER, REQUEST_BUILD (e.g. Prometheus)
        Sex secondSex = firstSex;
        do{
            firstSex = firstSex.getOppositeSex();
            for (Worker worker :
                    player.getMyWorkers()) {
                keepPlaying = keepPlaying || this.anyValidBuild(worker);
                if(keepPlaying)
                    break;
            }
        }while(firstSex != secondSex);
        return !keepPlaying;
    }
}
