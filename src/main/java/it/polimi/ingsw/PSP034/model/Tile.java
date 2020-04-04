package it.polimi.ingsw.PSP034.model;
import it.polimi.ingsw.PSP034.constants.*;

public class Tile {
    private Worker worker;
    private int building;
    private boolean dome;
    private final int x, y;

    /**
     *Initializes a new tile with (x,y) coordinates.
     * At the moment of creation the tile has no worker (set to null), building (set to 0) nor dome (set to false).
     * @param x x coordinate of the new tile
     * @param y y coordinate of the new tile
     */
    public Tile(int x, int y){
        worker = null;
        building = Constant.GROUND;
        dome = false;
        this.x = x;
        this.y = y;
    }

    public Worker getWorker(){
        return worker;
    }

    /**
     * Set the worker standing in this tile to newWorker.
     * @param newWorker Reference to the worker to be positioned in the tile
     * @throws IllegalArgumentException If newWorker is null an exception is thrown. removeWorker should be used instead.
     */
    public void setWorker(Worker newWorker) throws IllegalArgumentException{
        if(newWorker != null)
            worker = newWorker;
        else
            throw new IllegalArgumentException("\nCannot set worker to null, use removeWorker() instead\n");
    }

    public void removeWorker(){
        worker = null;
    }

    public int getBuilding(){
        return building;
    }

    /**
     * Sets the height of the building on this tile.
     * @param height the height of the building to set. height must be between 0 and 3
     * @throws IllegalArgumentException If height is not between 0 and 3 the function throws an exception
     */
    public void setBuilding(int height) throws IllegalArgumentException{
        if(Constant.GROUND <= height  &&  height <= Constant.LEVEL_THREE)
            building = height;
        else
            throw new IllegalArgumentException("\n"+height+" is not an acceptable height. Height must be between 0 and 3\n");
    }

    public boolean hasDome(){
        return dome;
    }

    public void setDome(boolean hasDome){
        dome = hasDome;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    /**
     * Checks if two tiles are neighbours (but not the same).
     * @param tile Reference to the tile to compare to.
     * @return true only if the given tile is neighbour.
     */
    public boolean isNeighbouringTile(Tile tile){
        int xDistance = Math.abs(x - tile.getX());
        int yDistance = Math.abs(y - tile.getY());
        if (xDistance == 0 && yDistance == 0)
            return false;
        else
            return ((xDistance <= 1) && (yDistance <= 1));
    }

}
