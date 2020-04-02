package it.polimi.ingsw.PSP034.model;

public class Board {
    private final Tile[][] tiles;

    /**
     * Initializes a new board of size 5x5 with tiles that do not have either a worker or a building
     */
    public Board(){
        tiles  = new Tile[5][5];
        for(int x=0; x<5; x++){
            for(int y=0; y<5; y++){
                tiles[x][y] = new Tile(x, y);
            }
        }
    }

    /**
     * Returns the reference to the tile at the given coordinates
     * @param x x coordinate of the needed tile
     * @param y y coordinate of the needed tile
     * @return reference to the tile with coordinates (x;y), if it exists, otherwise null
     */
    public Tile getTile(int x, int y){
        if((0 <= x)  &&  (x <= 4)  &&  (0 <= y)  &&  (y <= 4)){
            return tiles[x][y];
        }
        return null;
    }
}
