package it.polimi.ingsw.PSP034.model;

import it.polimi.ingsw.PSP034.constants.Sex;

/**
 * Interface containing the methods that form the proper game rules.
 */
public interface IRules {

    Tile getPreviousTile();

    Sex getChosenSex();

    DefaultRules getDefaultRules();

    /**
     * Moves a given worker to the given tile
     *
     * @param worker          Reference to the worker to be moved.
     * @param destinationTile Reference to the destination of the action.
     */
    void move(Worker worker, Tile destinationTile);

    /**
     * Builds a new level (or dome, following Default construction rules) on the given tile.
     *
     * @param buildTile Reference to the tile to be modified.
     */
    void build(Tile buildTile);

    /**
     * Method checks if a given move is valid.
     *
     * @param worker          Reference to the worker to be moved.
     * @param destinationTile Reference to the destination tile.
     * @return {@code true} if move is valid, {@code false} otherwise.
     */
    boolean validMove(Worker worker, Tile destinationTile);

    /**
     * Method checks if a given build is valid.
     *
     * @param worker       Reference to the worker who is building.
     * @param buildingTile Reference to the building tile.
     * @return {@code true} if build is valid, {@code false} otherwise.
     */
    boolean validBuild(Worker worker, Tile buildingTile);

    /**
     * Method applies winning condition rules.
     *
     * @param worker Reference to the Worker that was moved.
     * @return {@code true} if a Worker actively moved from level TWO to level THREE.
     */
    boolean checkWin(Worker worker);

    void setChosenSex(Worker chosenWorker);

    void setPreviousTile(Tile tile);

    /**
     * Checks if a given worker has any valid move available.
     *
     * @param worker Reference to the worker to be checked
     * @return true if the given worker has at least one available possible move.
     */
    boolean anyValidMove(Worker worker);

    /**
     * Method checks if there are any possible moves for a given player, checking both workers.
     *
     * @param player Reference to the player checked.
     * @return {@code true} if there are no moves left so that the player lost, {@code false} if the player
     * can keep playing, as at least there is one possible move left.
     */
    boolean checkMoveLost(Player player);

    /**
     * Checks if a given worker has any valid build available.
     *
     * @param worker Reference to the worker to be checked.
     * @return {@code true} if the given worker has at least one available possible build.
     */
    boolean anyValidBuild(Worker worker);

    /**
     * Method checks if there are any possible build actions for a given player, checking both workers.
     *
     * @param player Reference to the player checked.
     * @return {@code true} if there are no build actions left so that the player lost, {@code false} if the player
     * can keep playing, as at least there is one possible build actions left.
     */
    boolean checkBuildLost(Player player);
}
