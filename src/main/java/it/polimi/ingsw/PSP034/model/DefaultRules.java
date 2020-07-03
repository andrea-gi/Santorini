package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;

/**
 * Contains default action rules used in the game.
 */
public class DefaultRules implements IRules {
    private Sex chosenSex;
    private Tile previousTile;
    private final Game game;

    /**
     * Creates an instance linked to the current game.
     *
     * @param game Game to be associated.
     */
    public DefaultRules(Game game){
        this.game = game;
        this.chosenSex = null;
        this.previousTile = null;
    }

    /**
     * Notifies the model observers of a change in the board.
     */
    public void modelUpdated(){
        game.notifyObservers(game.generateSlimBoard());
    }

    public Sex getChosenSex() {
        return chosenSex;
    }

    public void setChosenSex(Worker chosenWorker) {
        this.chosenSex = chosenWorker.getSex();
    }

    public Tile getPreviousTile() {
        return previousTile;
    }

    @Override
    public DefaultRules getDefaultRules() {
        return this;
    }

    public void setPreviousTile(Tile previousTile) {
        this.previousTile = previousTile;
    }

    /**
     * Moves a given worker to the given tile
     *
     * @param worker          Reference to the worker to be moved.
     * @param destinationTile Reference to the destination of the action.
     */
    @Override
    public void move(Worker worker, Tile destinationTile) {
        setPreviousTile(worker.getMyTile());
        worker.getMyTile().setWorker(null);
        destinationTile.setWorker(worker);
        worker.setMyTile(destinationTile);
        setChosenSex(worker);
    }


    /**
     * Builds a new level (or dome, following Default construction rules) on the given tile.
     *
     * @param buildTile Reference to the tile to be modified.
     */
    @Override
    public void build(Tile buildTile) {
        int currentLevel = buildTile.getBuilding();
        if (currentLevel < Constant.LEVEL_THREE) {
            buildTile.setBuilding(currentLevel + 1);
        } else {
            buildTile.setDome(true);
        }
    }


    /**
     * Method checks if a given move is valid, following Default Rules.
     *
     * @param worker          Reference to the worker to be moved.
     * @param destinationTile Reference to the destination tile.
     * @return {@code true} if move is valid, {@code false} otherwise.
     */
    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {

        //Checks valid move up (at most 1 level)
        if (worker.heightDifference(destinationTile) > 1)
            return false;

        //Checks if moving tile is the same
        if(worker.getMyTile().getX() == destinationTile.getX()
                && worker.getMyTile().getY() == destinationTile.getY())
            return false;

        //Checks if tile is unoccupied
        if (destinationTile.getWorker() != null)
            return false;

        //Checks if tile does not have a dome
        if (destinationTile.hasDome())
            return false;

        //Checks if tiles are neighbour
        return worker.getMyTile().isNeighbouringTile(destinationTile);
    }


    /**
     * Method checks if a given build is valid, following Default Rules.
     *
     * @param worker       Reference to the worker who is building.
     * @param buildingTile Reference to the building tile.
     * @return {@code true} if build is valid, {@code false} otherwise.
     */
    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
        //Checks if building tile is the same
        if(worker.getMyTile().getX() == buildingTile.getX()
                && worker.getMyTile().getY() == buildingTile.getY())
            return false;

        //Checks if worker is the same used during move
        if (worker.getSex() != getChosenSex())
            return false;

        //Checks if tile is unoccupied
        if (buildingTile.getWorker() != null)
            return false;

        //Checks if tile does not have already a dome
        if (buildingTile.hasDome())
            return false;

        //Checks if tiles are neighbour
        return worker.getMyTile().isNeighbouringTile(buildingTile);
    }

    /**
     * Method applies normal winning condition rules.
     *
     * @param worker Reference to the Worker that was moved.
     * @return true if a Worker actively moved from level TWO to level THREE
     */
    @Override
    public boolean checkWin(Worker worker) {
        int lastLevel = previousTile.getBuilding();
        int currentLevel = worker.getMyTile().getBuilding();
        return (lastLevel == Constant.LEVEL_TWO && currentLevel == Constant.LEVEL_THREE);
    }


    /**
     * Checks if a given worker has any valid move available.
     *
     * @param worker Reference to the worker to be checked.
     * @return {@code true} if the given worker has at least one available possible move.
     */
    @Override
    public boolean anyValidMove(Worker worker) {
        boolean anyMove = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            anyMove = validMove(worker, neighbour);
            if (anyMove) {
                break;
            }
        }
        return anyMove;
    }

    /**
     * Method checks if there are any possible moves for a given player, checking both workers.
     *
     * @param player Reference to the player checked.
     * @return {@code true} if there are no moves left so that the player lost, {@code false} if the player
     * can keep playing, as at least there is one possible move left.
     */
    @Override
    public boolean checkMoveLost(Player player) {
        boolean keepPlaying = false;
        for (Worker worker :
                player.getMyWorkers()) {
            keepPlaying = anyValidMove(worker);
            if (keepPlaying) {
                break;
            }
        }
        return !keepPlaying;
    }

    /**
     * Checks if a given worker has any valid build available.
     *
     * @param worker Reference to the worker to be checked.
     * @return true if the given worker has at least one available possible build.
     */
    @Override
    public boolean anyValidBuild(Worker worker) {
        boolean anyBuild = false;
        for (Tile neighbour : worker.getMyTile().getNeighbouringTiles()) {
            anyBuild = validBuild(worker, neighbour);
            if (anyBuild) {
                break;
            }
        }
        return anyBuild;
    }

    /**
     * Method checks if there are any possible build actions for a given player, checking both workers.
     *
     * @param player Reference to the player checked.
     * @return {@code true} if there are no build actions left so that the player lost, {@code false} if the player
     * can keep playing, as at least there is one possible build actions left.
     */
    @Override
    public boolean checkBuildLost(Player player) {
        boolean keepPlaying = false;
        Sex savedSex = chosenSex;
        do{
            chosenSex = chosenSex.getOppositeSex();
            for (Worker worker :
                    player.getMyWorkers()) {
                keepPlaying = keepPlaying || anyValidBuild(worker);
                if(keepPlaying)
                    break;
            }
        }while(chosenSex != savedSex);
        return !keepPlaying;
    }

}
