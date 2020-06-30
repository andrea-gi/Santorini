package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;

/**
 * Represents a worker, associated to a player and on the game board.
 */
public class Worker {
    private final Sex sex;
    private final PlayerColor color;
    private final String name;
    private Tile myTile;

    /**
     * Initializes a worker and places it on a chosen tile.
     * @param sex       Sex of the worker being placed.
     * @param name      Player associated to the Worker.
     * @param color     Color of worker.
     * @param myTile    Starting worker tile.
     */
    public Worker(Sex sex, String name, PlayerColor color, Tile myTile) {
        this.sex = sex;
        this.name = name;
        this.color = color;
        this.myTile = myTile;
    }

    public PlayerColor getColor(){
        return color;
    }

    public Tile getMyTile(){
        return myTile;
    }

    /**
     * Sets the tile on which the worker is now.
     * @param tile  Tile the worker is being set on.
     * @throws      IllegalArgumentException if the tile is null.
     */
    public void setMyTile(Tile tile) {
        if (tile == null)
            throw new IllegalArgumentException("Tile not valid");
        myTile = tile;
    }

    public Sex getSex() {
        return sex;
    }

    public String getOwner() {
        return name;
    }

    /**
     * Calculates height difference between given tile and worker.
     * Positive if worker has to move up to reach the tile, negative otherwise.
     * @param tile  Reference to the tile to be checked.
     * @return      Integer representing level difference.
     */
    public int heightDifference(Tile tile){
        return tile.getBuilding() - this.myTile.getBuilding();
    }
}
