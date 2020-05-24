package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;

public class Worker {
    private final Sex sex;
    private final PlayerColor color;
    private final String name;
    private Tile myTile;

    /**Builds a worker and it places it in a chosen tile
     * @param sex indicates which worker you are building
     * @param name indicates the Player associated to the Worker
     * @param color indicates the color of my worker
     * @param myTile indicates where the player sets the worker the first turn  */
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

    /** Sets the tile on which the Worker is now
     * @param tile is the new tile, it must be allowed by the game rules
     * @throws IllegalArgumentException if the tile is null*/
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
     * Calculates height difference between given tile and worker. Positive if worker has to move up to reach the tile, negative otherwise.
     * @param tile Reference to the tile to be checked.
     * @return integer representing level difference.
     */
    public int heightDifference(Tile tile){
        return tile.getBuilding() - this.myTile.getBuilding();
    }
}
