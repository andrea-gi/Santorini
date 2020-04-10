package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;

public class Board {
    private final Tile[][] tiles;

    /**
     * Initializes a new board of size 5x5 with tiles that do not have either a worker or a building
     */
    public Board(){
        tiles  = new Tile[Constant.DIM][Constant.DIM];
        for(int x=0; x<Constant.DIM; x++){
            for(int y=0; y<Constant.DIM; y++){
                tiles[x][y] = new Tile(x, y);
            }
        }
        Tile.setBoard(this);
    }

    /**
     * Returns the reference to the tile at the given coordinates
     * @param x x coordinate of the needed tile
     * @param y y coordinate of the needed tile
     * @return reference to the tile with coordinates (x;y), if it exists, otherwise null
     */
    public Tile getTile(int x, int y){
        if(Tile.validCoordinates(x,y)){
            return tiles[x][y];
        }
        return null;
    }
}
