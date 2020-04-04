package it.polimi.ingsw.PSP034.controller;

import it.polimi.ingsw.PSP034.constants.Constant;
import it.polimi.ingsw.PSP034.model.Player;
import it.polimi.ingsw.PSP034.model.Tile;
import it.polimi.ingsw.PSP034.model.Worker;

import java.util.ArrayList;

/**
 * Contains default action rules used in the game.
 */
public class DefaultRules implements IRules {
    private Worker chosenWorker;

    public Worker getChosenWorker() {
        return chosenWorker;
    }

    public void setChosenWorker(Worker chosenWorker) {
        this.chosenWorker = chosenWorker;
    }

    /**
     * Moves a given worker to the given tile
     * @param worker Reference to the worker to be moved.
     * @param destinationTile Reference to the destination of the action.
     */
    @Override
    public void move(Worker worker, Tile destinationTile){
        worker.getMyTile().setWorker(null);
        destinationTile.setWorker(worker);
        worker.setMyTile(destinationTile);
        setChosenWorker(worker);
    }


    /**
     * Builds a new level (or dome, following Default construction rules) on the given tile.
     * @param buildTile Reference to the tile to be modified.
     */
    @Override
    public void build(Tile buildTile){
        int currentLevel = buildTile.getBuilding();
        if (currentLevel < Constant.LEVEL_THREE){
            buildTile.setBuilding(currentLevel + 1);
        }
        else{
            buildTile.setDome(true);
        }
    }


    /**
     * Method checks if a given move is valid, following Default Rules.
     * @param player Reference to the player who wants to perform the action.
     * @param worker Reference to the worker to be moved.
     * @param destinationTile Reference to the destination tile.
     * @return Returns true if move is valid, false otherwise.
     */
    @Override
    public boolean validMove(Player player, Worker worker, Tile destinationTile){
        //Checks ownership of worker
        if ( !player.isOwner(worker) )
            return false;

        //Checks valid move up (at most 1 level)
        if(worker.heightDifference(destinationTile) > 1)
            return false;

        //Checks if tile is unoccupied
        if( destinationTile.getWorker() != null )
            return false;

        //Checks if tile does not have a dome
        if( destinationTile.hasDome() )
            return false;

        //Checks if tiles are neighbour
        return worker.getMyTile().isNeighbouringTile(destinationTile);
    }


    /**
     * Method checks if a given build is valid, following Default Rules.
     * @param player Reference to the player who wants to perform the action.
     * @param worker Reference to the worker who is building.
     * @param buildingTile Reference to the building tile.
     * @return Returns true if build is valid, false otherwise.
     */
    @Override
    public boolean validBuild(Player player, Worker worker, Tile buildingTile){
        //Checks ownership of worker
        if ( !player.isOwner(worker) )
            return false;

        //Checks if worker is the same used during move
        if ( worker.getSex() != chosenWorker.getSex())
            return false;

        //Checks if tile is unoccupied
        if( buildingTile.getWorker() != null )
            return false;

        //Checks if tile does not have already a dome
        if ( buildingTile.hasDome() )
            return false;

        //Checks if tiles are neighbour
        return worker.getMyTile().isNeighbouringTile(buildingTile);
    }
}
