package it.polimi.ingsw.PSP034.model;

public class Worker {
    private final char sex;
    private final String name;
    private Tile myTile;

    /**Builds a worker in a non-valid tile; the player has to set the correct tile in the first turn
     * @param sex indicates which worker you are building
     * @param name indicates the Player associated to the Worker*/
    public Worker(char sex, String name) {
        this.sex = sex;
        this.name = name;
        myTile = null;
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

    public char getSex() {
        return sex;
    }

    public String getOwner() {
        return name;
    }
}
