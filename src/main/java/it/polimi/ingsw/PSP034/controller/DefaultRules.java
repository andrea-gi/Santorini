package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.constants.Sex;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

/**
 * Contains default action rules used in the game.
 */
public class DefaultRules implements IRules {
    private Sex chosenSex;
    private Tile previousTile;


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
     * @return Returns true if move is valid, false otherwise.
     */
    @Override
    public boolean validMove(Worker worker, Tile destinationTile) {

        //Checks valid move up (at most 1 level)
        if (worker.heightDifference(destinationTile) > 1)
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
     * @return Returns true if build is valid, false otherwise.
     */
    @Override
    public boolean validBuild(Worker worker, Tile buildingTile) {
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
     * @return Returns true if a Worker actively moved from level TWO to level THREE
     */
    @Override
    public boolean checkWin(Worker worker) {
        int lastLevel = previousTile.getBuilding();
        int currentLevel = worker.getMyTile().getBuilding();
        return (lastLevel == Constant.LEVEL_TWO && currentLevel == Constant.LEVEL_THREE);
    }


    /**
     * Checks if a given worker has any valid move available
     * @param worker Reference to the worker to be checked
     * @return Returns true if the given worker has at least one available possible move.
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
     * Checks if a given worker has any valid build available
     * @param worker Reference to the worker to be checked
     * @return Returns true if the given worker has at least one available possible build.
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
